import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './css/Month.css';

const Month = () => {
    const { monthid } = useParams();
  const numericMonthId = parseInt(monthid); // Convert to a number
  const [budgetData, setBudgetData] = useState([]);
  const [incomeData, setIncomeData] = useState([]); 
  const [newBudgetCategory, setNewBudgetCategory] = useState('');
  const [newBudgetAmount, setNewBudgetAmount] = useState('');
  const [addingBudget, setAddingBudget] = useState(false);
  const [addingIncome, setAddingIncome] = useState(false);
  const [newIncomeCategory, setNewIncomeCategory] = useState(''); // New state for income category
  const [newIncomeAmount, setNewIncomeAmount] = useState(''); // New state for income amount
  const [expenditureData, setExpenditureData] = useState([]);
  const [addingExpenditure, setAddingExpenditure] = useState(false);
  const [newExpenditureCategory, setNewExpenditureCategory] = useState('');
  const [newExpenditureAmount, setNewExpenditureAmount] = useState('');
  const [initialSavings, setInitialSavings] = useState(null);
  const [initialSavingsAvailable, setInitialSavingsAvailable] = useState(true);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpenditure, setTotalExpenditure] = useState(0); 

  const budgetCategories = ['Food', 'Transport', 'Medical', 'Utilities', 'Groceries', 'Rent', 'Entertainment'] ; // List of available categories
  const incomeCategories = ['Salary', 'Owed Money', 'Investments', 'Other']; // List of available income categories
  const expenditureCategories = ['Food', 'Transport', 'Medical', 'Utilities', 'Groceries', 'Rent', 'Entertainment']; // List of available expenditure categories  

  const navigate = useNavigate();

  const handleBackToHome = () => {
    navigate('/home');
  };

  const handlePromptInput = () => {
    navigate(`/input/${monthid}`);
  };

  const handleLogout = () => {
    localStorage.removeItem('user'); // Clear user data from local storage
    navigate('/login'); // Navigate to login page
  };

  const handleSaveExpenditure = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };
  
      const newExpenditureEntry = {
        monthid: numericMonthId,
        category: newExpenditureCategory,
        expenditure: parseFloat(newExpenditureAmount),
      };
  
      await axios.post(`http://localhost:8080/api/createexpenditure/${numericMonthId}`, newExpenditureEntry, config);
  
      window.location.reload();
  
      // Reset input fields and state
      setNewExpenditureCategory('');
      setNewExpenditureAmount('');
      setAddingExpenditure(false);
    } catch (error) {
      console.error('Error adding expenditure:', error);
    }
  };
  
  const handleCancelExpenditure = () => {
    setAddingExpenditure(false);
    setNewExpenditureCategory('');
    setNewExpenditureAmount('');
  };
  const handleDeleteExpenditure = async (expenditureId) => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };
  
      await axios.delete(`http://localhost:8080/api/deleteexpenditure/${expenditureId}`, config);
      window.location.reload();
    } catch (error) {
      console.error('Error deleting expenditure:', error);
    }
  };  
  const handleAddExpenditureClick = () => {
    setAddingExpenditure(true);
  };

  const handleAddBudgetClick = () => {
    setAddingBudget(true);
  };

const handleAddIncomeClick = () => {
    setAddingIncome(true);
  };

  const handleSaveIncome = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };

      const newIncomeEntry = {
        monthid: numericMonthId,
        category: newIncomeCategory,
        income: parseFloat(newIncomeAmount),
      };

      await axios.post(`http://localhost:8080/api/createincome/${numericMonthId}`, newIncomeEntry, config);
      window.location.reload();

      // Reset input fields and state
      setNewIncomeCategory('');
      setNewIncomeAmount('');
      setAddingIncome(false);
    } catch (error) {
      console.error('Error adding income:', error);
    }
  };

  const handleCancelIncome = () => {
    setAddingIncome(false);
    setNewIncomeCategory('');
    setNewIncomeAmount('');
  };
  
const handleDeleteBudget = async (budgetId) => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };

      await axios.delete(`http://localhost:8080/api/deletebudget/${budgetId}`, config);
      window.location.reload();

    } catch (error) {
      console.error('Error deleting budget:', error);
    }
  };
  
  const handleDeleteIncome = async (incomeId) => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };

      await axios.delete(`http://localhost:8080/api/deleteincome/${incomeId}`, config);
      window.location.reload();
    } catch (error) {
      console.error('Error deleting income:', error);
    }
  };

const handleSaveBudget = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };
  
      const currentDate = new Date();
      const formattedDate = currentDate.toISOString().split('T')[0]; // Get the "yyyy-mm-dd" format
  
      const newBudgetEntry = {
        monthid: numericMonthId,
        category: newBudgetCategory,
        budget: parseFloat(newBudgetAmount),
        date: formattedDate, // Use the formatted date
      };
  
      await axios.post(`http://localhost:8080/api/createbudget/${numericMonthId}`, newBudgetEntry, config);
  
      window.location.reload();
  
      // Reset input fields and state
      setNewBudgetCategory('');
      setNewBudgetAmount('');
      setAddingBudget(false);
    } catch (error) {
      console.error('Error adding budget:', error);
    }
  };
  

  const handleCancelBudget = () => {
    setAddingBudget(false);
    setNewBudgetCategory('');
    setNewBudgetAmount('');
  };

  useEffect(() => {
    const fetchBudgetData = async () => {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`
        }
      };
      try {
        const response = await axios.get(`http://localhost:8080/api/getbudgetmonth/${numericMonthId}`, config);
        setBudgetData(response.data);
      } catch (error) {
        console.error('Error fetching budget data:', error);
        if (error.response) {
          console.log('Response Data:', error.response.data);
          console.log('Response Status:', error.response.status);
        }
      }
      
    };

    fetchBudgetData();
  }, [monthid]);

  useEffect(() => {
    const fetchIncomeData = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const config = {
          headers: {
            Authorization: `Bearer ${user.accessToken}`,
          },
        };

        const response = await axios.get(`http://localhost:8080/api/getincome/${numericMonthId}`, config);
        setIncomeData(response.data);
      } catch (error) {
        console.error('Error fetching income data:', error);
      }
    };

    fetchIncomeData();
  }, [monthid]);

useEffect(() => {
    const fetchExpenditureData = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const config = {
          headers: {
            Authorization: `Bearer ${user.accessToken}`,
          },
        };

        const response = await axios.get(`http://localhost:8080/api/getexpenditure/${numericMonthId}`, config);
        setExpenditureData(response.data);
      } catch (error) {
        console.error('Error fetching income data:', error);
      }
    };

    fetchExpenditureData();
  }, [monthid]);

  const fetchInitialSavings = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      const config = {
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      };

      const response = await axios.get(
        `http://localhost:8080/api/getsavings/${numericMonthId}`,
        config
      );
      setInitialSavings(response.data);
    } catch (error) {
      console.error('Error fetching initial savings:', error);
    }
  };

  useEffect(() => {
    fetchInitialSavings();
  }, [monthid]);
  


  
  useEffect(() => {

    const fetchTotalIncome = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const config = {
          headers: {
            Authorization: `Bearer ${user.accessToken}`,
          },
        };

        const response = await axios.get(`http://localhost:8080/api/gettotalincome/${numericMonthId}`, config);
        setTotalIncome(response.data);
      } catch (error) {
        console.error('Error fetching total income:', error);
      }
    };

    fetchTotalIncome();
  }, [monthid]);

  useEffect(() => {
    const fetchTotalExpenditure = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const config = {
          headers: {
            Authorization: `Bearer ${user.accessToken}`,
          },
        };

        const response = await axios.get(`http://localhost:8080/api/gettotalbymonth/${numericMonthId}`, config);
        setTotalExpenditure(response.data);
      } catch (error) {
        console.error('Error fetching total expenditure:', error);
      }
    };

    fetchTotalExpenditure();
  }, [numericMonthId]);

  return (

    
    <div className="card pseudo-navbar body">
  <div className="card-body d-flex justify-content-between">
    <div>
      <button className="btn btn-secondary" onClick={handleBackToHome}>
        Back to Home
      </button>
      <button className="btn btn-primary" onClick={handlePromptInput}>
        Prompt Input
      </button>
    </div>
    <div>
      <button className="btn btn-danger" onClick={handleLogout}>
        Logout
      </button>
    </div>
  </div>
  <div className="row">
  <div className="col-md-6">
      {/* Budget Card */}
      <div className="card budget-card">
        <div className="card-header d-flex justify-content-between align-items-center">
          <h2>Budget</h2>
          {!addingBudget ? (
            <button className="btn btn-primary add-budget-button" onClick={handleAddBudgetClick}>
              Add Budget
            </button>
          ) : (
            <div>
              <div>
                <button className="btn btn-success" onClick={handleSaveBudget}>
                  Save
                </button>
                <button className="btn btn-danger" onClick={handleCancelBudget}>
                  Cancel
                </button>
              </div>
            </div>
          )}
        </div>
        <table className="table budget-table">
          {/* Table Header */}
          <thead>
            <tr>
              <th>Category</th>
              <th>Budget</th>
              <th></th>
            </tr>
          </thead>
          {/* Table Body */}
          <tbody>
            {budgetData.map((item, index) => (
              <tr key={index}>
                <td>{item.category}</td>
                <td>{item.budget}</td>
                <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDeleteBudget(item.id)}
                >
                  Delete
                </button>
              </td>
              </tr>
            ))}
            {/* New budget entry input fields */}
            {addingBudget && (
              <tr>
                <td>
                  <select
                    value={newBudgetCategory}
                    onChange={(e) => setNewBudgetCategory(e.target.value)}
                  >
                    <option value="">Select a category</option>
                    {budgetCategories.map((category, index) => (
                      <option key={index} value={category}>
                        {category}
                      </option>
                    ))}
                  </select>
                </td>
                <td>
                  <input
                    type="number"
                    placeholder="Amount"
                    value={newBudgetAmount}
                    onChange={(e) => setNewBudgetAmount(e.target.value)}
                  />
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
</div><div className="col-md-6">
      <div className="card financial-tally-card">
  <div className="card-header">
    <h2>Financial Tally</h2>
  </div>
  <div className="card-body">
    <table className="table">
      <thead>
        <tr>
          <th>Description</th>
          <th>Amount</th>
        </tr>
      </thead>
      <tbody>
      <tr>
  <td>Initial Savings</td>
  <td>{initialSavings}</td>
</tr>


        <tr>
            <td>Total Income</td>
            <td>{totalIncome}</td>
        </tr>
        <tr>
                <td>Total Expenditure</td>
                <td>{totalExpenditure}</td>
              </tr>
              <tr>
              <td>Savings Balance</td>
<td>
  {initialSavingsAvailable ? (
    initialSavings !== null ? (
      <span>
        {parseFloat(initialSavings) + totalIncome - totalExpenditure}
      </span>
    ) : (
      <div>
        Please Add Initial Savings
      </div>
    )
  ) : (
    <div>
      
    </div>
  )}
</td>

              </tr>

      </tbody>
    </table>
  </div>
</div>
</div>

</div>
<div className="row">
  <div className="col-md-6">
      <div className="card income-card">
        <div className="card-header">
          <div className="d-flex justify-content-between align-items-center">
            <h2>Income</h2>
            {!addingIncome ? (
              <button className="btn btn-primary" onClick={handleAddIncomeClick}>
                Add Income
              </button>
            ) : (
              <div>
                <button className="btn btn-success" onClick={handleSaveIncome}>
                  Save
                </button>
                <button className="btn btn-danger" onClick={handleCancelIncome}>
                  Cancel
                </button>
              </div>
            )}
          </div>
        </div>
        <div className="card-body">
          <table className="table">
            <thead>
              <tr>
                <th>Category</th>
                <th>Amount</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {incomeData.map((item, index) => (
                <tr key={index}>
                  <td>{item.category}</td>
                  <td>{item.income}</td>
                  <td>
                  <button
                      className="btn btn-danger"
                      onClick={() => handleDeleteIncome(item.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {/* New income entry input fields */}
              {addingIncome && (
                <tr>
                  <td>
                    <select
                      value={newIncomeCategory}
                      onChange={(e) => setNewIncomeCategory(e.target.value)}
                    >
                      <option value="">Select an income category</option>
                      {incomeCategories.map((category, index) => (
                        <option key={index} value={category}>
                          {category}
                        </option>
                      ))}
                    </select>
                  </td>
                  <td>
                    <input
                      type="number"
                      placeholder="Amount"
                      value={newIncomeAmount}
                      onChange={(e) => setNewIncomeAmount(e.target.value)}
                    />
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
      </div>
      <div className="col-md-6">
       <div className="card expenditure-card">
        <div className="card-header">
          <div className="d-flex justify-content-between align-items-center">
            <h2>Expenditure</h2>
            {!addingExpenditure ? (
              <button className="btn btn-primary" onClick={handleAddExpenditureClick}>
                Add Expenditure
              </button>
            ) : (
              <div>
                <button className="btn btn-success" onClick={handleSaveExpenditure}>
                  Save
                </button>
                <button className="btn btn-danger" onClick={handleCancelExpenditure}>
                  Cancel
                </button>
              </div>
            )}
          </div>
        </div>
        <div className="card-body">
          <table className="table">
            <thead>
              <tr>
                <th>Category</th>
                <th>Expenditure</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {expenditureData.map((item, index) => (
                <tr key={index}>
                  <td>{item.category}</td>
                  <td>{item.expenditure}</td>
                  <td>
                    <button
                      className="btn btn-danger"
                      onClick={() => handleDeleteExpenditure(item.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {/* New expenditure entry input fields */}
              {addingExpenditure && (
                <tr>
                  <td>
                    <select
                      value={newExpenditureCategory}
                      onChange={(e) => setNewExpenditureCategory(e.target.value)}
                    >
                      <option value="">Select an expenditure category</option>
                      {expenditureCategories.map((category, index) => (
                        <option key={index} value={category}>
                          {category}
                        </option>
                      ))}
                    </select>
                  </td>
                  <td>
                    <input
                      type="number"
                      placeholder="Amount"
                      value={newExpenditureAmount}
                      onChange={(e) => setNewExpenditureAmount(e.target.value)}
                    />
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div></div></div>
  );
};

export default Month;
