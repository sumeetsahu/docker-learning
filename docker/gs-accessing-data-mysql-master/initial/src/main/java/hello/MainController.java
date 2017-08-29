package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller    
@RequestMapping(path="/demo") 
public class MainController {

	
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@GetMapping(path="/department/add") 
	public @ResponseBody Department addDepartment (	@RequestParam String name, 
													@RequestParam String email, 
													@RequestParam(defaultValue = "") String hodId) {
		
		Department dept = new Department();
		dept.setName(name);
		dept.setEmail(email);
		
		if(hodId != null && !hodId.isEmpty())
		{
			Long id = Long.parseLong(hodId);
			if(empRepository.findOne(id)==null) {
				return null;
			}
			dept.setHodId(id);
		}
		
		deptRepository.save(dept);
		return dept;
	}
	
	@GetMapping(path="/department/{deptId}/update") 
	public @ResponseBody Department updateDepartment (	@PathVariable String deptId, 
														@RequestParam String name, 
														@RequestParam String email, 
														@RequestParam(defaultValue = "") String hodId) {
		
		Long id = Long.parseLong(deptId);
		Department dept = deptRepository.findOne(id);
		
		dept.setName(name);
		dept.setEmail(email);
		
		if(hodId != null && !hodId.isEmpty())
		{
			Long empid = Long.parseLong(hodId);
			
			if(empRepository.findOne(empid)==null) {
				return null;
			}
			dept.setHodId(empid);
		}
		
		deptRepository.save(dept);
		return dept;
	}
	
	@GetMapping(path="/department/all") 
	public @ResponseBody Iterable<Department> getAllDept () {

		return deptRepository.findAll();
		
	}
	
	@GetMapping(path="/department/{deptId}") 
	public @ResponseBody Department getDept (@PathVariable String deptId) {
		
		try {
			Long id = Long.parseLong(deptId);
			Department dept = deptRepository.findOne(id);
			return dept;
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/delete") 
	public @ResponseBody Response deleteDept (@PathVariable String deptId) {
		
		try {
			Long id = Long.parseLong(deptId);			
			deptRepository.delete(id);
			return new Response(ERRORCODE.SUCCESS);

		} catch(Exception e)
		{
			return new Response(ERRORCODE.EXCEPTION,e.getMessage());
		}		
		
		
	}
	
	@GetMapping(path="/department/all/delete") 
	public @ResponseBody Response deleteAllDept () {
		
		try {				
			deptRepository.deleteAll();;
			return new Response(ERRORCODE.SUCCESS, "Deleted All Successfully");
		} catch(Exception e)
		{
			return new Response(ERRORCODE.EXCEPTION,e.getMessage());
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/employee/all") 
	public @ResponseBody Iterable<Employee> listAllEmployee (@PathVariable String deptId) {
		
		try {	
			Long id = Long.parseLong(deptId);
			List<Employee> employees = new ArrayList<Employee>();

			for (Employee employee : empRepository.findAll()) {
				if(employee.getDepartmentId()==id) {
					employees.add(employee);
				}
					
			}
			
			return employees;
			
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/employee") 
	public @ResponseBody Iterable<Employee> listEmployee (	@PathVariable String deptId,
															@RequestParam String name) {

		
		try {	
			Long id = Long.parseLong(deptId);
			List<Employee> employees = new ArrayList<Employee>();

			for (Employee employee : empRepository.findAll()) {
				if(employee.getDepartmentId()==id && employee.getName().contains(name)) {
					employees.add(employee);
				}
					
			}
			
			return employees;
			
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/hod") 
	public @ResponseBody Employee getHod (@PathVariable String deptId) {
		
		try {	
			Long id = Long.parseLong(deptId);
			Long hodId = deptRepository.findOne(id).getHodId();
			Employee hod = empRepository.findOne(hodId);
						
			return hod;
			
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	
	
	@GetMapping(path="/employee/add") 
	public @ResponseBody Employee addEmployee (	@RequestParam String name, 
												@RequestParam String email, 
												@RequestParam String deptId) {


		Employee emp = new Employee();
		emp.setName(name);
		emp.setEmail(email);
		
		Long id = Long.parseLong(deptId);
		
		if(deptRepository.findOne(id)==null){
			return null;
		}
		
		emp.setDepartment(id);
		
			
		empRepository.save(emp);
		return emp;
	}
	
	@GetMapping(path="/employee/{empId}/update") 
	public @ResponseBody Employee updateEmployee (	@PathVariable String empId, 
													@RequestParam String name, 
													@RequestParam String email, 
													@RequestParam String deptId) {


		Long id = Long.parseLong(empId);
		Employee emp = empRepository.findOne(id);
		
		emp.setName(name);
		emp.setEmail(email);
		
		Long deptid = Long.parseLong(deptId);
		if(deptRepository.findOne(deptid)==null){
			return null;
		}
		
		emp.setDepartment(deptid);
		
			
		empRepository.save(emp);
		return emp;
	}
	
	@GetMapping(path="/employee/all") 
	public @ResponseBody Iterable<Employee> getAllEmp () {
		
		return empRepository.findAll();
		
	}
	
	@GetMapping(path="/employee/{empId}") 
	public @ResponseBody Employee getEmp (@PathVariable String empId) {
		
		try {
			Long id = Long.parseLong(empId);
			Employee emp = empRepository.findOne(id);
			return emp;
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/employee/{empId}/delete") 
	public @ResponseBody Response deleteEmployee (@PathVariable String empId) {
		
		try {
			Long id = Long.parseLong(empId);			
			empRepository.delete(id);
			return new Response(ERRORCODE.SUCCESS);
		} catch(Exception e)
		{
			return new Response(ERRORCODE.EXCEPTION,e.getMessage());
		}				
		
	}
	
	@GetMapping(path="/employee/all/delete") 
	public @ResponseBody Response deleteAllEmployee () {
		
		try {				
			empRepository.deleteAll();;
			return new Response(ERRORCODE.SUCCESS, "Deleted All Successfully");
		} catch(Exception e)
		{
			return new Response(ERRORCODE.EXCEPTION,e.getMessage());
		}		
		
	}		
	
}