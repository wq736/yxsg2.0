package com.yxsg.proxy;

/**
 * 商品相关的aop
 */

import java.io.File;
import java.util.Map;
import com.yxsg.exception.NameException;
import com.yxsg.exception.NumberException;
import com.yxsg.mapper.CommentMapper;
import com.yxsg.mapper.OrderItemMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.yxsg.bean.PageBean;
import com.yxsg.bean.Shop;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.utils.Utils;

@Aspect
@Component
public class ShopProxy{
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CommentMapper commentMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderItemMapper orderItemMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	
	/**
	 * 添加商品之前查看店铺中是否存在该商品
	 * 添加商品之后上传对应的文件
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Around(value = "execution(* com.yxsg.service.impl.ShopServiceImpl.addShop(..))")
	public PageBean<Shop> addShopAround(ProceedingJoinPoint point) {
		Map<String, String> data =  (Map<String, String>) point.getArgs()[0];
		PageBean<Shop> result = null;
		Integer stoId = Integer.valueOf(data.get("stoId"));
		if(shopMapper.selNameByName(stoId, data.get("shopName")) != null) {
			throw new NameException("用户添加商品失败！店铺中存在该商品！");
		}
		try {
			result = (PageBean<Shop>) point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String path = point.getArgs()[2] + "";
		MultipartFile pic[] = (MultipartFile[]) point.getArgs()[1];
		Utils.up(Utils.shopPicBname, pic[0], path);
		for (int i = 1; i < pic.length; i++) {
			if(pic[i].isEmpty()) {break;}
			Utils.up(Utils.shopPicSname + i, pic[i], path);
		}
		data.clear();
		result.setList(shopMapper.selShopByStoId(stoId, data, 0, result.getPageSize()));
		return result;
	}
	
	/**
	 * 删除商品之前判断是否有未处理的订单，有则不允许删除
	 * 删除商品之后删除商品对应的图片
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	@Around(value="execution(* com.yxsg.service.impl.ShopServiceImpl.delMyShop(..))")
	public PageBean<Shop> delShopAround(ProceedingJoinPoint point) throws Throwable {
		Map<String, String> data = (Map<String, String>) point.getArgs()[0];

		//查看是否有未处理的订单
		String shopName = data.get("shopName");
		String stoId = data.get("stoId") + "";
		Integer canDelOrderItem = orderItemMapper.selNotHandleCount(shopName, stoId);
		if(canDelOrderItem != 0) {throw new NumberException("删除失败！该商品存在尚未处理的相关订单！");}
		orderItemMapper.updScan(data, "N");

		String shopId = data.get("shopId");
		commentMapper.delComByShop(shopId, null);

		//执行被增强的方法
		PageBean<Shop> shopPage = (PageBean<Shop>) point.proceed();

		//删除文件
		String t = shopId.substring(shopId.indexOf("-") + 1);
		String shopPicUrl = Utils.picPath + data.get("uId") + "\\store\\" + t;
		String commPicUrl = Utils.picPath + "comment\\" + stoId + "\\" + shopId;
		Utils.delShopPic(new File(shopPicUrl));
		Utils.delShopPic(new File(commPicUrl));
		return shopPage;
	}
}
