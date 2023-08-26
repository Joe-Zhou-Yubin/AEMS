import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function Home() {
    const [months, setMonths] = useState([]);
    const jwtToken = localStorage.getItem('jwtToken');
    const user = JSON.parse(localStorage.getItem('user'));
    const username = user ? user.username : null;
    const roles = user ? user.roles : [];
    const navigate = useNavigate(); // Initialize the navigate function

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('user');
        navigate('/login'); // Redirect to the login page
      };

  const fetchMonths = async () => {
    try {
      if (username) {
        const response = await axios.get(`http://localhost:8080/api/getmonths/${username}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });
        setMonths(response.data);
      }
    } catch (error) {
      console.error('Error fetching months:', error);
    }
  };

  useEffect(() => {
    fetchMonths();
  }, [username, jwtToken]);

  
  const handleCreateBudgetClick = async () => {
    const inputDate = prompt('Please enter a date in MM-YYYY format:');
    
    if (inputDate) {
      const isValidFormat = /^[0-9]{2}-[0-9]{4}$/.test(inputDate);
      
      if (isValidFormat) {
        const formattedDate = inputDate;
        
        try {
          const response = await axios.post(
            'http://localhost:8080/api/createmonth',
            { monthYear: formattedDate },
            {
              headers: {
                Authorization: `Bearer ${user.accessToken}`,
              },
            }
          );
          console.log('Response:', response.data);
          // Refresh months after creating new budget
          fetchMonths();
        } catch (error) {
          console.error('Error creating monthly budget:', error);
        }
      } else {
        alert('Invalid date format. Please use MM-YYYY format.');
      }
    }
  };
  
  const deleteButtonStyles = {
    backgroundColor: '#d9534f', // Red color
    color: '#fff',
    border: 'none',
    padding: '5px 10px',
    borderRadius: '5px',
    cursor: 'pointer',
    marginTop: '5px',
    alignSelf: 'flex-end', // Align to the bottom right
  };

  const handleDeleteMonth = async (monthId) => {
    try {
      // Assuming you have an API endpoint to delete a month
      await axios.delete(`http://localhost:8080/api/deletemonth/${monthId}`, {
        headers: {
          Authorization: `Bearer ${user.accessToken}`, // Attach the JWT token
        },
      });
      window.location.reload();
      // Here you can update your UI or perform any necessary actions
    } catch (error) {
      console.error('Delete error:', error);
    }
  };

  const handleUserDashboardClick = () => {
    navigate('/user'); // Navigate to the "/user" route
  };
  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <h1>Monthly Cards</h1>
        {roles.includes('ROLE_MANAGER') && (
          <button onClick={handleUserDashboardClick}>User Dashboard</button>
        )}
        <button onClick={handleCreateBudgetClick}>Create Monthly Budget</button>
        <button onClick={handleLogout}>Logout</button> {/* Logout button */}

      </div>
      <div className="card-container">
        {months.map((month) => (
          <div key={month.id} className="card">
            <Link to={`/month/${month.id}`} style={{ textDecoration: 'none' }}>
              <p>{month.monthYear}</p>
            </Link>
            <button
              style={deleteButtonStyles}
              onClick={() => handleDeleteMonth(month.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
);
}

export default Home;
