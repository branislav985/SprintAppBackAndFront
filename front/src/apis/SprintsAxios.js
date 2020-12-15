import axios from 'axios';

var SprintsAxios = axios.create({
  baseURL: 'http://localhost:8080/api',
  /* other custom settings */
});

export default SprintsAxios;