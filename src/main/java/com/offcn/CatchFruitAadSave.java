package com.offcn;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

import com.offcn.po.Fruit;
import com.offcn.service.FruitService;

public class CatchFruitAadSave {
	
	
	/*public static void main(String[] args) throws Exception {
		sendEmail();
	}*/
	
	@Autowired
	FruitService fruitService;
	
	@Autowired
	
	JavaMailSenderImpl mailSender;
	
	//定时执行方法
	@Scheduled(cron="0/30 * * * * ?")
	public  void start() throws Exception{
		//clearDB();
		System.out.println("开始抓取");
		for(int i=1; i<=10; i++){
			String url = "http://www.xinfadi.com.cn/marketanalysis/2/list/"+i+".shtml?prodname=%E7%BA%A2%E6%8F%90%E8%91%A1%E8%90%84&begintime=1970-01-01&endtime=2018-08-12";
			//1.获取html文档
			String html = catUrlHtml(url);
			//System.out.println(html);
			//2.解析html文档并返回所需数据集合
			List<Fruit> list = parseHtml(html);
			//System.out.println(list);
			//3.将爬取到的数据保存到数据库
			saveFruit(list);			
			Thread.sleep(1000);
		}
		//4.从数据库中获取所需数据并绘制PDF
		MakePDF.makePDF();
		//5.发送邮件到指定邮箱
		sendEmail();
	}
	
	//1.根据请求路径获取html文档
	public  String catUrlHtml(String url) throws Exception {
		String html = "";
		//建立客户端对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//建立get对象
		HttpGet get = new HttpGet(url);
		//设置请求头
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		//发送get请求
		CloseableHttpResponse response = httpClient.execute(get);
		//根据状态码执行操作
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
		}else{
			System.out.println("请求失败！"+response.getStatusLine().getStatusCode());
		}
		httpClient.close();
		return html;
	}
	
	//2.解析html
	public static List<Fruit> parseHtml(String html) throws Exception{
		List<Fruit> list = new ArrayList<Fruit>();
		Document doc = Jsoup.parse(html);
		//开始解析
		
		//1.选取class=hq_table的table
		//2.选取td[style=text-align:left;padding-left:5px;]的tds
		//3.遍历tds 
		//4.获取每个td的父节tr,获取该tr下的所有td节点,并将其中的数据绑定到fruit对象容器中
		Elements fruits = doc.select(".hq_table").first().select("td[style=text-align:left;padding-left:5px;]");
		
		//System.out.println(fruits);
		for (Element element : fruits) {
			Fruit fruit = new Fruit();
			Element tr = element.parent();
			Elements items = tr.select("td");
			fruit.setName(items.get(0).text());
			fruit.setMinprice(Double.parseDouble(items.get(1).text()));
			fruit.setAvgprice(Double.parseDouble(items.get(2).text()));
			fruit.setMaxprice(Double.parseDouble(items.get(3).text()));
			fruit.setCategory(items.get(4).text());
			fruit.setUnit(items.get(5).text());
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			fruit.setDateofissue(simpleDateFormat.parse((items.get(6).text())));
			list.add(fruit);
			
		}
		
		//System.out.println(fruits);
		
		return list;
	}
	
	//3.保存到数据库
	public void saveFruit(List<Fruit> list){
		
		for (Fruit fruit : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String nowDate = sdf.format(fruit.getDateofissue()).toString();
			System.out.println("当前日期："+nowDate);
			Fruit exitFruit = fruitService.getFruitByDate(nowDate);
			if(exitFruit==null){
				fruitService.save(fruit);				
			}
		}
		
		System.out.println("保存成功");
	}
	
	//4.清除数据库
	public void clearDB(){
		
		fruitService.deleteAll();
		System.out.println("数据库已清除");
	
	}
	
	//5.发送邮件
	public  void sendEmail() throws Exception{
		
		//获取带附件的邮件对象
		MimeMessage mime = mailSender.createMimeMessage();
		//创建助手类
		MimeMessageHelper helper = new MimeMessageHelper(mime, true);
		helper.setFrom("986101339@qq.com");
		helper.setTo("986101339@qq.com");
		helper.setSubject("新发地水果价格走势");
		helper.setText("红提葡萄价格走势");
		
		File file1 = new File("d:\\chart\\fruitAvgPrice.pdf");
		helper.addAttachment("fruitAvgPrice.pdf", file1);
		
		
		
		mailSender.send(mime);
		
		System.out.println("1send ok!");
		
	
		
	}
	
	
	
}
