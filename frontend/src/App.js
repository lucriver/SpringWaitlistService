import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [messageType, setMessageType] = useState('');

  useEffect(() => {
    console.log('App component mounted');
    document.title = 'Waitlist Form';
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form submitted with email:', email);

    try {
      console.log('Sending request to server...');
      const response = await axios.post('/waitlist', { email });
      console.log('Server response:', response);

      setMessage('Email added successfully!');
      setMessageType('success');
    } catch (error) {
      console.log('Server error:', error);
      setMessage(error.response?.data?.message || 'Error adding email.');
      setMessageType('error');
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', backgroundColor: '#f0f0f0' }}>
      <div style={{ padding: '20px', backgroundColor: 'white', borderRadius: '10px', boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)', width: '300px' }}>
        <h1 style={{ fontSize: '20px', marginBottom: '10px' }}>Join the Waitlist</h1>
        {/* Add text to make sure rendering is happening */}
        <p style={{ marginBottom: '10px' }}>Enter your email to join our waitlist</p>

        <form onSubmit={handleSubmit}>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={{ width: '100%', padding: '8px', marginBottom: '10px', borderRadius: '5px', border: '1px solid #ccc' }}
          />
          <button
            type="submit"
            style={{
              width: '100%',
              padding: '8px',
              backgroundColor: '#007bff',
              color: 'white',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer'
            }}
            onClick={() => console.log('Button clicked')}
          >
            Submit
          </button>
        </form>

        {message && (
          <div style={{ marginTop: '10px', padding: '8px', color: 'white', borderRadius: '5px', backgroundColor: messageType === 'success' ? '#28a745' : '#dc3545' }}>
            {message}
          </div>
        )}
      </div>
    </div>
  );
}

// Make sure the component is being exported correctly
export default App;