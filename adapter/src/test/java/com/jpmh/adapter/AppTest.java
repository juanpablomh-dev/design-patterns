package com.jpmh.adapter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class AppTest{
	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> App.main(new String[]{}));
	}
}
