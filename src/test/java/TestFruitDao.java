import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.FruitDao;

public class TestFruitDao {
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		FruitDao fruitDao = context.getBean(FruitDao.class);
		
		List<Double> avglist = fruitDao.getAvgAsYM(2018, 7);
		
		System.out.println(avglist);
		System.out.println(avglist.size());
	
		context.close();
	}

}
