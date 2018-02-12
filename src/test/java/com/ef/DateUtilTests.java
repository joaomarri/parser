package com.ef;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.ef.util.DateUtil;
import com.ef.util.DurationEnum;

@RunWith(SpringRunner.class)
public class DateUtilTests {

	private DateUtil dtUtil = new DateUtil();
	
	@Test
	public void test_add_one_hour_in_date() throws ParseException {
		Date date21Hours = dtUtil.toDate("2018-02-11.21:00:00");
		
		Date date22Horus = dtUtil.addHours(date21Hours, DurationEnum.hourly.getHours());
		Date date = dtUtil.toDate("2018-02-11.22:00:00");
		Assert.assertTrue(date22Horus.equals(date));
		
	}
	
	@Test
	public void test_add_24_hour_in_date() throws ParseException {
		Date date21Hours = dtUtil.toDate("2018-02-11.21:00:00");
		
		Date newDay = dtUtil.addHours(date21Hours, DurationEnum.daily.getHours());
		Date date = dtUtil.toDate("2018-02-12.21:00:00");
		Assert.assertTrue(newDay.equals(date));
		
	}

}
