package com.workappinc.workappserver.businesslogic.model.table;

import com.workappinc.workappserver.dataaccess.orm.annotations.Table;

/**
 * Logically Mapped, POJO for user table
 * 
 * @author dhgovindaraj
 *
 */

@Table(name = "user")
public class User
{
	String id;
	String service_key;
	String email;
	String password;
	String phone;
	String first_name;
	String last_name;
	boolean is_worker;
	boolean is_individual;
	String employer_name;
	String occupation;
	String skills;
	boolean is_owner;

	@Override
	public String toString()
	{
		return "User [id=" + id + ", service_key=" + service_key + ", email=" + email + ", password=" + password + ", phone=" + phone + ", first_name=" + first_name + ", last_name=" + last_name + ", is_worker=" + is_worker + ", is_individual=" + is_individual + ", employer_name=" + employer_name + ", occupation=" + occupation + ", skills=" + skills + ", is_owner=" + is_owner + ", hashCode()=" + hashCode() + ", getId()=" + getId() + ", getService_key()=" + getService_key() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword() + ", getPhone()=" + getPhone() + ", getFirst_name()=" + getFirst_name() + ", getLast_name()=" + getLast_name() + ", getIs_customer()=" + getIs_worker() + ", getIs_individual()=" + getIs_individual() + ", getEmployer_name()=" + getEmployer_name() + ", getOccupation()=" + getOccupation() + ", getSkills()=" + getSkills() + ", getIs_owner()=" + getIs_owner() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((employer_name == null) ? 0 : employer_name.hashCode());
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (is_worker ? 1231 : 1237);
		result = prime * result + (is_individual ? 1231 : 1237);
		result = prime * result + (is_owner ? 1231 : 1237);
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((service_key == null) ? 0 : service_key.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (employer_name == null)
		{
			if (other.employer_name != null)
				return false;
		}
		else if (!employer_name.equals(other.employer_name))
			return false;
		if (first_name == null)
		{
			if (other.first_name != null)
				return false;
		}
		else if (!first_name.equals(other.first_name))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (is_worker != other.is_worker)
			return false;
		if (is_individual != other.is_individual)
			return false;
		if (is_owner != other.is_owner)
			return false;
		if (last_name == null)
		{
			if (other.last_name != null)
				return false;
		}
		else if (!last_name.equals(other.last_name))
			return false;
		if (occupation == null)
		{
			if (other.occupation != null)
				return false;
		}
		else if (!occupation.equals(other.occupation))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		}
		else if (!password.equals(other.password))
			return false;
		if (phone == null)
		{
			if (other.phone != null)
				return false;
		}
		else if (!phone.equals(other.phone))
			return false;
		if (service_key == null)
		{
			if (other.service_key != null)
				return false;
		}
		else if (!service_key.equals(other.service_key))
			return false;
		if (skills == null)
		{
			if (other.skills != null)
				return false;
		}
		else if (!skills.equals(other.skills))
			return false;
		return true;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getService_key()
	{
		return service_key;
	}

	public void setService_key(String service_key)
	{
		this.service_key = service_key;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public boolean getIs_worker()
	{
		return is_worker;
	}

	public void setIs_worker(boolean is_worker)
	{
		this.is_worker = is_worker;
	}

	public boolean getIs_individual()
	{
		return is_individual;
	}

	public void setIs_individual(boolean is_individual)
	{
		this.is_individual = is_individual;
	}

	public String getEmployer_name()
	{
		return employer_name;
	}

	public void setEmployer_name(String employer_name)
	{
		this.employer_name = employer_name;
	}

	public String getOccupation()
	{
		return occupation;
	}

	public void setOccupation(String occupation)
	{
		this.occupation = occupation;
	}

	public String getSkills()
	{
		return skills;
	}

	public void setSkills(String skills)
	{
		this.skills = skills;
	}

	public boolean getIs_owner()
	{
		return is_owner;
	}

	public void setIs_owner(boolean is_owner)
	{
		this.is_owner = is_owner;
	}

}
