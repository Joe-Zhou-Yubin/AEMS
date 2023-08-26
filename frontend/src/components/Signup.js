import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import pigIcon from './images/money.png';
import wallpaper from './images/wallpaper.jpg';

function Signup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const containerStyles = {
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f7f3f1',
    backgroundImage: `url(${wallpaper})`,
    backgroundSize: 'cover',
    margin: '0',
    padding: '0',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
  };

  const formStyles = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    color: '#1d2570',
    fontFamily: "'Times New Roman', Times, serif",
    fontSize: 'larger',
  };

  const inputStyles = {
    width: '300px',
    padding: '10px',
    margin: '5px 0',
    border: '1px solid #ccc',
    borderRadius: '15px',
  };

  const submitButtonStyles = {
    backgroundColor: '#879fce',
    color: '#fff',
    border: 'none',
    padding: '7px 15px',
    borderRadius: '5px',
    cursor: 'pointer',
    width: 'fit-content',
    alignSelf: 'center', // Center the button
    marginTop: '-10px',
  };

  const linkStyles = {
    color: '#1d2570',
    fontSize: 'small',
    textAlign: 'left',
    marginLeft: '10px',
  };

  const pigIconStyles = {
    width: '100px', // Increase the width for more padding to the right
    height: '100px',
    marginBottom: '10px',
    marginTop: '-45px',
    marginRight: '10px',
  };

  const h2Styles = {
    color: '#754393',
    textAlign: 'center',
    fontFamily: 'Verdana, Geneva, Tahoma, sans-serif',
    fontSize: 'xxx-large',
  };
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/signup', {
        username,
        password,
        email,
      });

      // Handle the response accordingly (e.g., show success message, redirect, etc.)
      console.log('Signup success:', response.data);
      navigate('/login');
    } catch (error) {
      console.error('Signup error:', error);
    }
  };

  return (
    <div style={containerStyles}>
      <img src={pigIcon} alt="Money Icon" style={pigIconStyles} />
      <h2 style={h2Styles}>Create Account</h2>
      <form style={formStyles} onSubmit={handleSignup}>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          style={inputStyles}
        />
        <br />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          style={inputStyles}
        />
        <br />
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          style={inputStyles}
        />
        <br />
        <input type="submit" value="Add Account" style={submitButtonStyles} />
      </form>
    </div>
  );
}

export default Signup;
