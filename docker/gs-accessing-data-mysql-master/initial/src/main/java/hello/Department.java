package hello;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.mapping.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="DEPARTMENT")
public class Department {

	@Id
    @Column(name = "ID", unique = true, nullable = false, precision = 15, scale = 0)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
    private String name;

    private String email;
    
    private Long hodId;
   
    
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

	public Long getHodId() {
		return hodId;
	}

	public void setHodId(Long hodId) {
		this.hodId = hodId;
	}


    
}
