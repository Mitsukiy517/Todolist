package com.todolist.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public class Task {

	private int id;

	@NotBlank(message = "日付を入力してください")
	private String date;

	@Max(value=30, message="最大文字数は30文字です")
	@NotBlank(message = "タイトルを入力してください")
	private String title;

	@Max(value=45, message="最大文字数は45文字です")
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
