package com.listen.model.bean;

import java.io.Serializable;

/**
 * 
 * @author Edward
 */
public class IndexBookCategoryBean implements Serializable{

	private String bookCategoryName;//图书分类名称
	private String bookCategoryImages;//图片

	public String getBookCategoryName() {
		return bookCategoryName;
	}

	public void setBookCategoryName(String bookCategoryName) {
		this.bookCategoryName = bookCategoryName;
	}

	public String getBookCategoryImages() {
		return bookCategoryImages;
	}

	public void setBookCategoryImages(String bookCategoryImages) {
		this.bookCategoryImages = bookCategoryImages;
	}
}
