package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

/**
 * Article generated by MyEclipse - Hibernate Tools
 */

public class Article implements java.io.Serializable {

	private Integer id;

	private String title;

	private String content;

	private Long create_time;

	private Integer cat_id;

	public Integer getCat_id() {
		return cat_id;
	}

	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	@PrimaryKeyField
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}