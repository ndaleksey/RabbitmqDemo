package com.alex;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
public class ConsumerApplicationTest {

	@Test(expected = NullPointerException.class)
	public void getPrettifiedString_stringIsNull() {
		ConsumerApplication.getPrettifiedString(null);
	}
}