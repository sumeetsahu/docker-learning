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

import hello.User;
import hello.UserRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Autowired
	private EmployeeRepository empRepository;

	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	
	@GetMapping(path="/department/add") // Map ONLY GET Requests
	public @ResponseBody Department addDepartment (@RequestParam String name
			, @RequestParam String email, @RequestParam(defaultValue = "") String hodId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

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
	
	@GetMapping(path="/department/{deptId}/update") // Map ONLY GET Requests
	public @ResponseBody Department updateDepartment (@PathVariable String deptId, @RequestParam String name
			, @RequestParam String email, @RequestParam(defaultValue = "") String hodId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

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
	
	@GetMapping(path="/department/all") // Map ONLY GET Requests
	public @ResponseBody Iterable<Department> getAllDept () {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		return deptRepository.findAll();
		
	}
	
	@GetMapping(path="/department/{deptId}") // Map ONLY GET Requests
	public @ResponseBody Department getDept (@PathVariable String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {
			Long id = Long.parseLong(deptId);
			Department dept = deptRepository.findOne(id);
			return dept;
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/delete") // Map ONLY GET Requests
	public @ResponseBody String deleteDept (@PathVariable String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {
			Long id = Long.parseLong(deptId);			
			deptRepository.delete(id);
			return "Deleted Successfully";
		} catch(Exception e)
		{
			return "Failed to delete!!!";
		}		
		
		
	}
	
	@GetMapping(path="/department/all/delete") // Map ONLY GET Requests
	public @ResponseBody String deleteAllDept () {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {				
			deptRepository.deleteAll();;
			return "Deleted All Successfully";
		} catch(Exception e)
		{
			return "Failed to delete all!!!";
		}		
		
		
	}
	
	@GetMapping(path="/department/{deptId}/employee/all") // Map ONLY GET Requests
	public @ResponseBody Iterable<Employee> listAllEmployee (@PathVariable String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
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
	
	@GetMapping(path="/department/{deptId}/employee") // Map ONLY GET Requests
	public @ResponseBody Iterable<Employee> listEmployee (@PathVariable String deptId,@RequestParam String name) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
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
	
	@GetMapping(path="/department/{deptId}/hod") // Map ONLY GET Requests
	public @ResponseBody Employee getHod (@PathVariable String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
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
	
	
	
	
	
	
	@GetMapping(path="/employee/add") // Map ONLY GET Requests
	public @ResponseBody Employee addEmployee (@RequestParam String name
			, @RequestParam String email, @RequestParam String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

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
	
	@GetMapping(path="/employee/{empId}/update") // Map ONLY GET Requests
	public @ResponseBody Employee updateEmployee (@PathVariable String empId, @RequestParam String name
			, @RequestParam String email, @RequestParam String deptId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

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
	
	@GetMapping(path="/employee/all") // Map ONLY GET Requests
	public @ResponseBody Iterable<Employee> getAllEmp () {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		return empRepository.findAll();
		
	}
	
	@GetMapping(path="/employee/{empId}") // Map ONLY GET Requests
	public @ResponseBody Employee getEmp (@PathVariable String empId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {
			Long id = Long.parseLong(empId);
			Employee emp = empRepository.findOne(id);
			return emp;
		} catch(Exception e)
		{
			return null;
		}		
		
		
	}
	
	@GetMapping(path="/employee/{empId}/delete") // Map ONLY GET Requests
	public @ResponseBody String deleteEmployee (@PathVariable String empId) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {
			Long id = Long.parseLong(empId);			
			empRepository.delete(id);
			return "Deleted Successfully";
		} catch(Exception e)
		{
			return "Failed to delete!!!";
		}		
		
		
	}
	
	@GetMapping(path="/employee/all/delete") // Map ONLY GET Requests
	public @ResponseBody String deleteAllEmployee () {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		try {				
			empRepository.deleteAll();;
			return "Deleted All Successfully";
		} catch(Exception e)
		{
			return "Failed to delete all!!!";
		}		
		
		
	}
		
	
}