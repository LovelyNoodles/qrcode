package com.qrcode.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

/**
 * @use 利用Java代码给图片加水印
 */
public class WaterMarkUtils {
	
	public static Image addWaterMark(String srcImgPath, String waterMarkContent) {
		return WaterMarkUtils.addWaterMark(srcImgPath, null, waterMarkContent,  Color.BLACK, new Font("微软雅黑", Font.PLAIN, 35));
	}

	/**
	 * @param srcImgPath
	 *            源图片路径
	 * @param tarImgPath
	 *            保存的图片路径
	 * @param waterMarkContent
	 *            水印内容
	 * @param markContentColor
	 *            水印颜色
	 * @param font
	 *            水印字体
	 * @return 
	 */
	public static Image addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent, Color markContentColor, Font font) {

		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);// 得到文件
			Image srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
			
			// 加水印
			int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
			int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			if (StringUtils.isNotBlank(waterMarkContent)) {
				Graphics2D g = bufImg.createGraphics();
				g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
				g.setColor(markContentColor); // 根据图片的背景设置水印颜色
				g.setFont(font); // 设置字体
				
				// 设置水印的坐标
				int x = (srcImgWidth - getWatermarkLength(waterMarkContent, g)) >> 1;
				int y = srcImgHeight - 15;
				g.drawString(waterMarkContent, x, y); // 画出水印
				g.dispose();
				
				// 输出图片
				if (StringUtils.isNotBlank(tarImgPath)) {
					FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
					ImageIO.write(bufImg, "jpg", outImgStream);
					System.out.println("添加水印完成");
					outImgStream.flush();
					outImgStream.close();
				}
			}
			
			// 返回图片
			return bufImg;
		} catch (Exception e) {
		}
		return null;
	}

	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public static void main(String[] args) {
		Font font = new Font("微软雅黑", Font.PLAIN, 35); // 水印字体
		String srcImgPath = "C:/Users/corwu/Desktop/1.jpg"; // 源图片地址
		String tarImgPath = "C:/Users/corwu/Desktop/2.jpg"; // 待存储的地址
		String waterMarkContent = "这我老婆"; // 水印内容
		Color color = Color.BLACK; // 水印图片色彩以及透明度
		WaterMarkUtils.addWaterMark(srcImgPath, tarImgPath, waterMarkContent, color, font);

	}

}