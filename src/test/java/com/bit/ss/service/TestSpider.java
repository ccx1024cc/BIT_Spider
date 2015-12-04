package com.bit.ss.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**   
 * @Title: TestSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  
 * @author CCX
 * @date 2015年12月4日 上午9:02:15 
 * @version V1.0   
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSpider {

	@Autowired
	@Qualifier("CampusNoticeSpider")
	private ISpiderService campusNoticeSpider;
	
	@Autowired
	@Qualifier("DailyStuffSpider")
	private ISpiderService dailyStuffSpider;
	
	@Autowired
	@Qualifier("EducationNoticeSpider")
	private ISpiderService educationNoticeSpider;
	
	@Autowired
	@Qualifier("HMSpider")
	private ISpiderService HMSpider;
	
	@Autowired
	@Qualifier("IndexNewsSpider")
	private ISpiderService indexNewsSpider;
	
	@Autowired
	@Qualifier("InternationComSpider")
	private ISpiderService internationComSpider;
	
	@Autowired
	@Qualifier("JobNoticeSpider")
	private ISpiderService jobNoticeSpider;
	
	@Autowired
	@Qualifier("JWCNoticeSpider")
	private ISpiderService JWCNoticeSpider;
	
	@Autowired
	@Qualifier("SchoolBusSpider")
	private ISpiderService schoolBusSpider;
	
	@Autowired
	@Qualifier("LectureSpider")
	private ISpiderService lectureSpider;
	
	@Autowired
	@Qualifier("NetworkNoticeSpider")
	private ISpiderService networkNoticeSpider;
	
	@Autowired
	@Qualifier("OfficeWorkSpider")
	private ISpiderService officeWorkSpider;
	
	@Autowired
	@Qualifier("PostGraduateEnrollSpider")
	private ISpiderService postGraduateEnrollSpider;
	
	@Autowired
	@Qualifier("ScienceStudyNoticeSpider")
	private ISpiderService scienceStudyNoticeSpider;
	
	@Autowired
	@Qualifier("StudentStuffSpider")
	private ISpiderService studentStuffSpider;
	
	@Autowired
	@Qualifier("UnderGraduateEnrollSpider")
	private ISpiderService underGraduateEnrollSpider;
	
	@Test
	public void testCampusNoticeSpider(){
		try {
			campusNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDailyStuffSpider(){
		try {
			dailyStuffSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEducationNoticeSpider(){
		try {
			educationNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHMSpider(){
		try {
			HMSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIndexNewsSpider(){
		try {
			indexNewsSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInternationComSpider(){
		try {
			internationComSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJobNoticeSpider(){
		try {
			jobNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJWCNoticeSpider(){
		try {
			JWCNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSchoolBusSpider(){
		try {
			schoolBusSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLectureSpider(){
		try {
			lectureSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNetworkNoticeSpider(){
		try {
			networkNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfficeWorkSpider(){
		try {
			officeWorkSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPostGraduateEnrollSpider(){
		try {
			postGraduateEnrollSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testScienceStudyNoticeSpider(){
		try {
			scienceStudyNoticeSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStudentStuffSpider(){
		try {
			studentStuffSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUnderGraduateEnrollSpider(){
		try {
			underGraduateEnrollSpider.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
