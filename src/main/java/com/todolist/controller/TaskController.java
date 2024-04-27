package com.todolist.controller;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todolist.model.Task;

@Controller
public class TaskController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/index")
	public String index(@ModelAttribute Task task, Model model) {
		String query = "SELECT * FROM my_app.todolist";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
		model.addAttribute("tasks", list);
		return "index";
	}

	@GetMapping("/form")
	public String readForm(@ModelAttribute Task task) {
		return "form";
	}

	@PostMapping("/form")
	public String confirm(@Validated Task task, BindingResult result, Model model) {
		if(checkDateFormat(task.getDate())){
			model.addAttribute("date_check", true);
			return "form";
		}
		else if(result.hasErrors()) {
			return "form";
		}
		
		String query = "INSERT INTO my_app.todolist (date, title, text) VALUES (?, ?, ?)";
		jdbcTemplate.update(query, task.getDate(), task.getTitle(), task.getText());
		return "redirect:/index";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		String query = "DELETE FROM my_app.todolist WHERE id = ?";
		jdbcTemplate.update(query, id);
		return "redirect:/index";
	}
	
	public boolean checkDateFormat(String date) {
		DateFormat format = DateFormat.getDateInstance();
		format.setLenient(false);
		if(date == null || date.length() != 10) {
			return true;
		}
		try {
			format.parse(date);
			return false;
		}catch(Exception e) {
			return true;
		}
				
	}
		

}
