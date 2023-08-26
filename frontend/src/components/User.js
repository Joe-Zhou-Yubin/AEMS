import React, { useState, useEffect } from 'react';
import axios from 'axios';
import wallpaper from './images/wallpaper.jpg'
import { useNavigate } from 'react-router-dom'; // Import useNavigate and useParams

const bodyStyles = {
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f7f3f1',
    backgroundImage: `url(${wallpaper})`,
    backgroundSize: 'cover',
    margin: '0',
    padding: '5px 10px',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh',
  };

const buttonStyles = {
  backgroundColor: '#D98D8D',
  border: '0',
  borderRadius: '5px',
  padding: '10px',
  fontSize: '16px',
  fontFamily: `'Times New Roman', Times, serif`,
  cursor: 'pointer',
};

const deleteButtonStyles = {
    backgroundColor: '#bfc1ce', // Original background color
    color: '#fff', // Add white text color for visibility
    border: 'none', // Remove border
    borderRadius: '5px',
    padding: '10px',
    fontSize: '16px',
    fontFamily: `'Times New Roman', Times, serif`,
    cursor: 'pointer',
    backgroundColor: 'red', // Change background color to red
  };

const titleStyles = {
  textAlign: 'center',
  fontFamily: 'Verdana, Geneva, Tahoma, sans-serif',
  fontSize: '48px',
  fontWeight: '800',
  lineHeight: '54px',
};



const tableStyles = {
  width: '100%',
  borderRadius: '5px',
  overflow: 'hidden',
};

const tableHeaderStyles = {
  backgroundColor: '#b28967be',
  color: '#fff',
  fontFamily: 'Verdana, Geneva, Tahoma, sans-serif',
  padding: '10px',
  textAlign: 'center',
};

const tableDataStyles = {
  backgroundColor: '#e0c4ac',
  textAlign: 'center',
};

const userNameStyles = {
    backgroundColor: '#e0c4ac',
  width: '40%',
  textAlign: 'center',
};

function User() {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/auth/users');
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const convertRoles = (roles) => {
    return roles.map((role) => {
      switch (role.name) {
        case 'ROLE_MANAGER':
          return 'Manager';
        case 'ROLE_MEMBER':
          return 'Member';
        // Add more cases if needed
        default:
          return role.name;
      }
    });
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleDelete = async (userId) => {
    try {
      await axios.delete(`http://localhost:8080/api/auth/delete/${userId}`);
      // Refresh the users list after deletion
      fetchUsers();
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };
  const handleGoBack = () => {
    navigate(`/home`);
  };
  return (
    <div className="Dashboard" style={bodyStyles}>
      <div className="homeBtn">
        <button onClick={handleGoBack} style={buttonStyles}>
          Back to Home
        </button>
      </div>
      <div className="title">
        <h1 style={titleStyles}>User Dashboard</h1>
      </div>
      <table style={tableStyles}>
        <thead>
          <tr>
            <th style={tableHeaderStyles}>Username</th>
            <th style={tableHeaderStyles}>Email</th>
            <th style={tableHeaderStyles}>Role(s)</th>
            <th style={tableHeaderStyles}>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td className="userName" style={userNameStyles}>
                {user.username}
              </td>
              <td style={tableDataStyles}>{user.email}</td>
              <td style={tableDataStyles}>{convertRoles(user.roles).join(', ')}</td>
              <td style={tableDataStyles}>
                <button className="deleteBtn" onClick={() => handleDelete(user.id)} style={deleteButtonStyles}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default User;
