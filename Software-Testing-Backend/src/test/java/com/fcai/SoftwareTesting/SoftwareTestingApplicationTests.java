package com.fcai.SoftwareTesting;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SoftwareTestingApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	String exampleTodoJson="{\r\n    \"title\": \"Hello\",\r\n    \"description\": \"Software testing\"\r\n}";
	@MockBean
	private TodoServiceImpl todoServiceImp;

	TodoServiceImpl todoService=new TodoServiceImpl();
	private static List<Todo> todos;
	TodoCreateRequest req =new TodoCreateRequest() ;
	Todo todo;
	private void arrange()
	{
		todos = new ArrayList<Todo>();
	}
	private void act(Todo todo) {
		todos.add(todo);
	}
	//	private void arrange(TodoCreateRequest req)
//	{   req=new TodoCreateRequest();
//		//todos = new ArrayList<Todo>();
//	}
	@Test
	void TodoServiceConst()
	{
		arrange();
		//assertEquals();
	}
	@Test
	void  CheckEmptyTodoObj_create()
	{   boolean Test=false;
		try {
			if(req!=null){
				Test=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test = false;
		}
		//Bug 1 -->Should be Null and return false but here it returns true
		assertFalse(Test);
	}
	@Test
	void CheckEmptyTitle_create()
	{   boolean Test=false;
		req.setTitle("");
		try {
			//System.out.println("nnnn");
			if(!req.getTitle().isEmpty()){
				Test=true;
				//	System.out.println("kkkkk");
			}
		} catch (IllegalArgumentException error)
		{
			//System.out.println("hhhhhhhhhhhhhhhhhh");
			Test = false;
		}
		assertFalse(Test);
		//return Test;
	}
	@Test
	void CheckEmptyDescription_create()
	{   boolean Test=false;
		req.setDescription("");
		try {
			//System.out.println("nnnn");
			if(!req.getDescription().isEmpty()){
				Test=true;
				//	System.out.println("kkkkk");
			}
		} catch (IllegalArgumentException error)
		{
			//System.out.println("hhhhhhhhhhhhhhhhhh");
			Test = false;
		}
		assertFalse(Test);
		//return Test;
	}

	@Test
	void CheckTest_create()
	{   arrange();
		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		todo=new Todo("1",req.getTitle(),req.getDescription(),false);
		act(todo);
		assertEquals(todo.getId(),todoService.create(req).getId());
		assertEquals(todo.getTitle(),todoService.create(req).getTitle());
		assertEquals(todo.getDescription(),todoService.create(req).getDescription());
		assertEquals(todo.isCompleted(),todoService.create(req).isCompleted());
	}

	@Test
	void CheckTest_list()
	{   boolean Test=false;
		//Bug 2 --> function list returns empty list not null value

		try {
			if(todoService.list()!=null){
				Test=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test = false;
		}
		assertFalse(Test);
		arrange();
		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		todo=todoService.create(req);
		act(todo);
		req.setTitle("Note2");
		req.setDescription("Go to Uni2");
		todo=todoService.create(req);
		act(todo);
		try {
			if(!todoService.list().isEmpty() ||todoService.list()!=null ){
				Test=false;
			}
		} catch (IllegalArgumentException error)
		{
			Test = true;
		}
		assertFalse(Test);
		assertEquals(2,todoService.list().size());
		for (int i=0;i<todoService.list().size();i++)
		{
			assertEquals(todos.get(i).getId(),todoService.list().get(i).getId());
			assertEquals(todos.get(i).getDescription(),todoService.list().get(i).getDescription());
			assertEquals(todos.get(i).getTitle(),todoService.list().get(i).getTitle());
			assertEquals(todos.get(i).isCompleted(),todoService.list().get(i).isCompleted());

		}
	}

	@Test
	void CheckTest_listCompleted()
	{

		boolean Test=false;
		//Bug 3 --> function list returns empty list not null value

		try {
			if(todoService.list()!=null){
				Test=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test = false;
		}
		assertFalse(Test);
		List<Todo> completedTodos = new ArrayList<>();

		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		todo=todoService.create(req);
		todo.setCompleted(true);
		completedTodos.add(todo);
		req.setTitle("Note2");
		req.setDescription("Go to Uni2");
		todo=todoService.create(req);
		todo.setCompleted(true);
		completedTodos.add(todo);
		req.setTitle("Note3");
		req.setDescription("Go to Uni3");
		todo=todoService.create(req);
		todo.setCompleted(false);
		req.setTitle("Note4");
		req.setDescription("Go to Uni4");
		todo=todoService.create(req);
		todo.setCompleted(false);
		assertEquals(2,todoService.listCompleted().size());
		for (int i = 0; i < todoService.listCompleted().size(); i++) {
			assertEquals(completedTodos.get(i).getId(),todoService.listCompleted().get(i).getId());
			assertEquals(completedTodos.get(i).getTitle(),todoService.listCompleted().get(i).getTitle());
			assertEquals(completedTodos.get(i).getDescription(),todoService.listCompleted().get(i).getDescription());
			assertEquals(completedTodos.get(i).isCompleted(),todoService.listCompleted().get(i).isCompleted());
		}

	}
	@Test
	void CheckEmptyId_read()
	{   boolean Test=false;
		boolean Test2=false;
		Todo todo=new Todo();
		try {
			if(todo.getId()!=null){
				Test=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test = false;
		}
		assertFalse(Test);

		todo.setId("");
		try {
			if(!todo.getId().isEmpty()){
				Test2=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test2 = false;
		}
		assertFalse(Test2);
	}

	@Test
	void CheckTest_read()
	{   boolean Test=false;
		try {
			if(todoService.read("1").getId()!=null){
				Test=true;
			}
		} catch (IllegalArgumentException error)
		{
			Test = false;
		}
		assertFalse(Test);
		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		assertEquals(todoService.create(req).getId(),todoService.read("1").getId());
	}

	@Test
	void CheckTest_update()
	{  //Bug 2 -->update doesn't work bec here isCompleted
		boolean isCompleted=true;
		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		todo=todoService.create(req);
		todo.setCompleted(true);

//		System.out.println(todo.isCompleted() );
//		System.out.println(todoService.update("1",isCompleted).isCompleted());
		assertEquals(todoService.list().get(0).isCompleted(),todoService.update("1",isCompleted).isCompleted());
		assertEquals(todoService.list().get(0).getId(),todoService.update("1",isCompleted).getId());
		assertEquals(todoService.list().get(0).getTitle(),todoService.update("1",isCompleted).getTitle());
		assertEquals(todoService.list().get(0).getDescription(),todoService.update("1",isCompleted).getDescription());

	}
	@Test
	void CheckTest_delete()
	{   int isDeleted=0;
		req.setTitle("Note1");
		req.setDescription("Go to Uni");
		todo=todoService.create(req);
		req.setTitle("Note2");
		req.setDescription("Go to Uni2");
		todoService.create(req);
		todoService.delete(todo.getId());
		for (int i=0;i<todoService.list().size();i++)
		{
			//System.out.println(todoService.list().get(i).getId());
			if (todoService.list().get(i).getId()==todo.getId())
				isDeleted++;

		}
		if (isDeleted==0)
		{
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
		//System.out.println(isDeleted);
		//assertTrue(isDeleted);


	}
	@Test
	void ControllerCreate() {

		Todo mocktodo=new Todo("1","Note1","Go to Uni",false);
		when(todoServiceImp.create(Mockito.any(TodoCreateRequest.class))).thenReturn(mocktodo);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create")
				.accept(MediaType.APPLICATION_JSON).content(exampleTodoJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost:8080/create", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	void ControllerRead()
	{   		Todo mocktodo=new Todo("1","Note1","Go to Uni",false);
		when(todoServiceImp.read(Mockito.getId())).thenReturn(mocktodo);


		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/read").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"id\":\"1\",\"title\":\"Note1\",\"description\":\"Go to Uni\"}";

		//{"id":"1","title":"bfbd","description":"fkdm","completed":false}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	@Test
	void ControllerUpdate() throws Exception
	{
		Todo mocktodo=new Todo("1","Note1","Go to Uni",false);
		when(todoServiceImp.update(mocktodo.getId(),true)).thenReturn(mocktodo);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/update").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"id\":\"1\",\"title\":\"Note1\",\"description\":\"Go to Uni\",\"completed\":true}";

		//{"id":"1","title":"bfbd","description":"fkdm","completed":false}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	@Test
	void Controllerdelete() throws Exception
	{
		String id="1";
		doNothing().when(todoServiceImp).delete(id);
		mockMvc.perform(delete("/delete",id))
				.andExpect(status().isNotFound())
				.andDo(print());
		//OR
//		String id="1";
//		when(todoServiceImp.delete(id)).thenReturn(mocktodo);
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
//				"/delete").accept(
//				MediaType.APPLICATION_JSON);

	}
	@Test
	void ControllerList() throws UnsupportedEncodingException, JSONException {
		Todo mocktodo=new Todo("1","Note1","Go to Uni",false);
		when(todoServiceImp.list()).thenReturn(mocktodo);


		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/list").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"id\":\"1\",\"title\":\"Note1\",\"description\":\"Go to Uni\"}";

		//{"id":"1","title":"bfbd","description":"fkdm","completed":false}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	@Test
	void  ControllerListCompleted() throws UnsupportedEncodingException, JSONException {
		Todo mocktodo=new Todo("1","Note1","Go to Uni",false);
		when(todoServiceImp.listCompleted()).thenReturn(mocktodo);


		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/listCompleted").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"id\":\"1\",\"title\":\"Note1\",\"description\":\"Go to Uni\"}";

		//{"id":"1","title":"bfbd","description":"fkdm","completed":false}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
}
