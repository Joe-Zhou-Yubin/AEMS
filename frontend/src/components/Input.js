import React, { useState } from 'react';
import axios from 'axios';
import pigIcon from './images/money.png';
import wallpaper from './images/wallpaper.jpg';
import { useNavigate, useParams } from 'react-router-dom'; // Import useNavigate and useParams

const backButtonStyles = {
  position: 'absolute',
  top: '20px',
  left: '20px',
  backgroundColor: '#879fce',
  color: '#fff',
  border: 'none',
  padding: '7px 15px',
  borderRadius: '5px',
  cursor: 'pointer',
  fontSize: 'large',
};

const inputStyles = {
  width: '800px',
  padding: '20px',
  margin: '5px 0',
  border: '1px solid #cccccc',
  backgroundColor: 'rgb(237, 237, 223)',
  borderRadius: '40px',
  fontSize: 'x-large',
};

const submitButtonStyles = {
  backgroundColor: '#879fce',
  color: '#fff',
  border: 'none',
  padding: '10px 15px',
  borderRadius: '5px',
  cursor: 'pointer',
  width: 'fit-content',
  alignSelf: 'flex-end',
  marginRight: '40px',
  marginTop: '10px', 
  fontSize: 'large',
};

const h2Styles = {
  color: '#754393',
  textAlign: 'center',
  fontFamily: 'Verdana, Geneva, Tahoma, sans-serif',
  fontSize: 'xxx-large',
};

const bodyStyles = {
  fontFamily: 'Arial, sans-serif',
  backgroundColor: '#f7f3f1',
  backgroundImage: `url(${wallpaper})`,
  backgroundSize: 'cover',
  margin: '0',
  padding: '0',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  height: '100vh',
};

const formStyles = {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  color: '#1d2570',
  fontFamily: "'Times New Roman', Times, serif",
  fontSize: 'larger',
  marginBottom: '20px', // Added margin to bottom of the form
};

const moneyIconStyles = {
  width: '100px',
  height: '100px',
  marginRight: '20px',
};

function Input() {
  const navigate = useNavigate();
  const { monthId } = useParams();
  const numericMonthId = parseInt(monthId);
  const [userInput, setUserInput] = useState('');

  const handleUserInput = (event) => {
    setUserInput(event.target.value);
  };

  const handleGoBack = () => {
    navigate(`/month/${numericMonthId}`);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      console.log("Submitting user input:", userInput);

      // Simulate a delay of 2 seconds (2000 milliseconds)
      await new Promise((resolve) => setTimeout(resolve, 2000));

      console.log("Sending POST request...");

      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };

      const response = await axios.post(`http://localhost:8080/api/createinput/${numericMonthId}`, {
        userinput: userInput,
      }, config); // Include the config here

      console.log("Response:", response.data);

      if (response.status === 201) {
        console.log("Navigation successful.");
        navigate(`/month/${numericMonthId}`);
      } else {
        console.log("Response status code:", response.status);
        // Handle other status codes if needed
      }
    } catch (error) {
      console.error('Error creating input:', error);
      // Handle the error state here
    }
  };

  return (
    <div style={bodyStyles}>
      <button onClick={handleGoBack} style={backButtonStyles}>
        Back to Month
      </button>
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <img src={pigIcon} alt="Money Icon" style={moneyIconStyles} />
        <h2 style={h2Styles}>Input your income/expenses</h2>
      </div>
      <form style={formStyles} onSubmit={handleSubmit}>
        <input
          type="text"
          name="input"
          required
          style={inputStyles}
          value={userInput}
          onChange={handleUserInput}
        />
        <input type="submit" value="Go" style={submitButtonStyles} />
      </form>
    </div>
  );
}

export default Input;
