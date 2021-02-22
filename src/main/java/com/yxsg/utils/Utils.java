package com.yxsg.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.yxsg.bean.PageBean;

/**
 * 工具类
 */
public class Utils {
	public static final Integer TAB_SIZE = 6; 				//表格每页显示的最大行数
	public static final Integer SHOP_SIZE = 24; 			//商品列表页每页显示的最大商品数
	public static final Integer Com_Size = 5; 				//评论区每页最大显示数
	public static final String shopPicBname = "bpicture";	//商品大图的名称(不含后缀名)
	public static final String shopPicSname = "spicture";	//商品小图的名称(不含后缀名)
	public static final String picPath = "D:\\idea\\projects\\userImg\\";	//图片路径

	/**
	 * 获取当前月所在季节的初始年月日
	 */
	public static String getBegin() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String[] monthArr = {"03", "06", "09", "12"};
		String month = monthArr[0];
		if(m >= 3) {
			month = monthArr[m / 3 - 1];
		} else {
			month = monthArr[3];
		}
		if(m == 1 || m==2) {year--;}
		return year + "-" + month + "-01";
	}

	/**
	 * 获取当前月所在季节的结束年月日
	 */
	public static String getEnd() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String begin = getBegin();
		String[] monthArr = {"06", "09", "12", "03"};
		int m = Integer.valueOf(begin.substring(5, 7));
		String month = monthArr[m / 3 - 1];
		if(month == "03") {year++;}
		return year + "-" + month + "-01";
	}

	public static String getPicName(MultipartFile mp){
		String oldName = mp.getOriginalFilename();		//获取原文件名
		return oldName.substring(oldName.lastIndexOf("."));		//获取原文件名的扩展名
	}

	/**
	 * 上传文件到指定路径
	 * @param name 图片名称(不含扩展名)
	 * @param mp
	 * @param path 上传路径（相对于类路径下static目录的路径）
	 */
	public static String up(String name, MultipartFile mp, String path) {
		String filePath = picPath + path;
		File file = new File(filePath);
		if(!file.exists()){file.mkdirs();}
		String fileName = filePath + name + getPicName(mp);		//为新文件名添加扩展名和路径
		try {
			mp.transferTo(new File(fileName));									//上传文件
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return fileName.substring(picPath.length()).replace("\\", "/");
	}

	/**
	 * 获取分页模型
	 */
	public static <T> PageBean<T> getPage(Integer pageCount, Integer pageSize) {
		Integer pageTotal = pageCount / pageSize;
		if(pageCount % pageSize != 0) {pageTotal++;}
		return new PageBean<T>(null, pageTotal, pageCount, pageSize);
	}

	/**
	 * 设置分页模型的开始页码与结束页码并修改当前页
	 */
	public static <T> PageBean<T> getPage(PageBean<T> page, Integer pageOn) {
		page.setPageOn(pageOn);
		Integer pageTotal = page.getPageTotal();
		if(pageTotal > 5) {
			if(pageOn > pageTotal - 2) {
				page.setPageBegin(pageTotal - 4);
				page.setPageEnd(pageTotal);
			} else if(pageOn > 3) {
				page.setPageBegin(pageOn - 2);
				page.setPageEnd(pageOn + 2);
			} else {
				page.setPageBegin(1);
				page.setPageEnd(5);
			}
		} else if(pageTotal <= 5 && pageTotal > 1) {
			page.setPageBegin(1);
			page.setPageEnd(pageTotal);
		} else {
			page.setPageBegin(0);
			page.setPageEnd(-1);
		}
		return page;
	}

	/**
	 * 删除记录时根据情况返回不同的sql查询语句中limit的范围以及分页模型的当前页、总记录数
	 */
	public static Map<String, Object> getLimPageParam(Integer pageCount, Integer pageOn, Integer pageTotal, Integer pageSize) {
		Map<String, Object> getLimPageParam = new HashMap<String, Object>();
		Integer begin = 0, size = 0;
		if(pageOn == pageTotal && pageCount % pageSize > 1 || pageTotal == 1) {
			begin = null;
		}  else {
			if(pageCount % pageSize == 1 && pageOn > 1) {
				pageOn--;
				begin = (pageOn - 1) * pageSize;
				size = pageSize;
			} else {
				begin = pageOn * pageSize - 1;
				size = 1;
			}
		}
		pageCount--;
		if(pageCount % pageSize == 0) {pageTotal--;}
		PageBean<Object> page = new PageBean<Object>(pageOn, pageTotal, pageCount, pageSize);
		getLimPageParam.put("page", page);
		getLimPageParam.put("begin", begin);
		getLimPageParam.put("size", size);
		return getLimPageParam;
	}

	/**
	 * 获取当前时间yyyy-MM-dd HH:mm:ss
	 */
	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 删除指定的文件(夹)
	 */
	public static void delShopPic(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fs : files) {
				delShopPic(fs);
			}
		}
		file.delete();
	}

	/**
	 * 根据状态码返回对应的状态
	 */
	public static String getStatus(Integer staCode) {
		if(staCode == -2) {return "注销";}
		if(staCode == -1) {return "正常";}
		if(staCode == 0) {return "永久禁用";}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis() + staCode * 1000 * 60 * 60 * 24);
		return "禁用至：" + format.format(date);
	}

	/**
	 * 获取禁用的时间
	 */
	public static String getDisableTime(String status) {
		if(!"正常".equals(status) && !"永久禁用".equals(status) && !"注销".equals(status)) {
			status = status.substring(4);
		}
		return status;
	}
}
