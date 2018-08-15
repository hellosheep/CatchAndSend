package com.offcn;

import java.awt.Font;
import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.offcn.dao.FruitDao;

public class MakePDF {
	
	public static void main(String[] args) throws Exception {
		makePDF();
		
	}
	
	public static void makePDF() throws Exception{
		
		PdfWriter writer = new PdfWriter(new File("d:\\chart\\fruitAvgPrice.pdf"));
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc = new Document(pdfDoc, PageSize.A2.rotate());
		PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);		
		
		//设置标题
		doc.add(new Paragraph("2017年3月与2018年3月同期红提葡萄平均价走势对比图(单位：元/斤)").setTextAlignment(TextAlignment.CENTER).setFontColor(Color.BLACK).setFontSize(26F).setFont(font));
		
		//获取所需数据
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		FruitDao fruitDao = context.getBean(FruitDao.class);
		
		List<Double> avglist2017_3 = fruitDao.getAvgAsYM(2017, 3);
		List<Double> avglist2018_3 = fruitDao.getAvgAsYM(2018, 3);
		
		//创建表格
		Table table = new Table(new float[avglist2018_3.size()+1]);
		table.setWidthPercent(100);
		
		System.out.println("2018:"+avglist2018_3.size());
		System.out.println("2017:"+avglist2017_3.size());
		
		//设置表格第一行
		for(int i=0; i<=avglist2018_3.size(); i++){
			if(i!=0){
				Cell c = new Cell().add(String.valueOf(i));				
				table.addCell(c);
			}else if(i==0){
				Cell c = new Cell().add("");				
				table.addCell(c);
			}
		}
		
		//设置表格第二行2017年数据
		for(int i=0; i<=avglist2018_3.size(); i++){
			if(i!=0){
				Cell c = new Cell().add(String.valueOf(avglist2017_3.get(i-1)));				
				table.addCell(c);
			}else if(i==0){
				Cell c = new Cell().add("2017��").setFontColor(Color.BLUE);				
				table.addCell(c);
			}
		}
		
		//设置表格第二行2018年数据
		for(int i=0; i<=avglist2018_3.size(); i++){
			if(i!=0){
				Cell c = new Cell().add(String.valueOf(avglist2018_3.get(i-1)));				
				table.addCell(c);
			}else if(i==0){
				Cell c = new Cell().add("2018��").setFontColor(Color.RED);				
				table.addCell(c);
			}
		}
		//表格渲染完毕
		//将表格加入到PDF中
		doc.add(table);
		
		//通过Jfreechart技术生成折线图
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//绘制2017折线图
		int temp2017 = 1;
		for (Double avg : avglist2017_3) {
			dataset.addValue(avg, "2017年", String.valueOf(temp2017));
			temp2017++;
		}
		//绘制2018折线图
		int temp2018 = 1;
		for (Double avg : avglist2018_3) {
			dataset.addValue(avg, "2018年", String.valueOf(temp2018));
			temp2018++;
		}
		StandardChartTheme theme = new StandardChartTheme("CN");
		theme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
		theme.setRegularFont(new Font("宋体", Font.BOLD, 20));
		theme.setLargeFont(new Font("宋体", Font.BOLD, 20));
		
		ChartFactory.setChartTheme(theme);
		
		//生成折线图
		JFreeChart chart = ChartFactory.createLineChart("平均价格走势对比图", "月份", "平均价格", dataset);
		
		//显示数据
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer render = (LineAndShapeRenderer) plot.getRenderer();
		render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		render.setBaseItemLabelsVisible(true);
		
		//将折线图从内存写入本地文件
		ChartUtilities.saveChartAsJPEG(new File("d:\\chart\\fruit.jpg"), chart, 1800, 600);
		System.out.println("fruit.jpg ok!");
		
		//将折线图加入PDF
		doc.add(new Image(ImageDataFactory.create("d:\\chart\\fruit.jpg")));
		
		
		
		context.close();
		
		doc.close();
		System.out.println("PDF OK!");
	}

}
