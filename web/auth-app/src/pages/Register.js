import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    fname: "",
    lname: "",
    email: "",
    password_hash: "",
    role: "USER"
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData)
    });

    if (response.ok) {
      alert("Registration successful!");
      navigate("/login");
    } else {
      alert("Registration failed.");
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} required /><br/>
        <input name="fname" placeholder="First Name" onChange={handleChange} /><br/>
        <input name="lname" placeholder="Last Name" onChange={handleChange} /><br/>
        <input name="email" placeholder="Email" onChange={handleChange} required /><br/>
        <input name="password_hash" type="password" placeholder="Password" onChange={handleChange} required /><br/>
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;