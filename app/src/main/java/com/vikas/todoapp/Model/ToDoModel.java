package com.vikas.todoapp.Model;

public class ToDoModel
{

	public enum MSort
	{
		NAME,
		DATE,
		SIZE
	}
	public enum MFilter
	{
		COMPLETED,
		PENDING,
		ALL
	}
	
	private String todo_title;
	private String todo_description;
	private String todo_status;
    private String todo_timestamp;

	public ToDoModel(String todo_title, String todo_description, String todo_status)
	{
		this.todo_title = todo_title;
		this.todo_description = todo_description;
		this.todo_status = todo_status;
	}
	
	public void setTodo_title(String todo_title)
	{
		this.todo_title = todo_title;
	}

	public String getTodo_title()
	{
		return todo_title;
	}

	public void setTodo_description(String todo_description)
	{
		this.todo_description = todo_description;
	}

	public String getTodo_description()
	{
		return todo_description;
	}

	public void setTodo_status(String todo_status)
	{
		this.todo_status = todo_status;
	}

	public String getTodo_status()
	{
		return todo_status;
	}
	
	public void setTodo_timestamp(String todo_timestamp)
	{
		this.todo_timestamp = todo_timestamp;
	}

	public String getTodo_timestamp()
	{
		return todo_timestamp;
	}
}
