package hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="EMPLOYEE")
public class Employee {
    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 15, scale = 0)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;

    private String email;
    
    private Long departmentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getDepartmentId() {
		return this.departmentId;
	}
	
	public void setDepartment(Long departmentId) {
		this.departmentId = departmentId;
	}


	

}
