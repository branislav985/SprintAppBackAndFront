import React from "react";
import { Form, Button, Col, Row, Jumbotron } from "react-bootstrap";

import {login} from '../../services/auth';

class Login extends React.Component {
  constructor() {
    super();

    this.state = { username: "", password: "" };
  }

  valueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let change = {};
    change[name] = value;
    this.setState(change);
  }

  doLogin(){
    login(this.state);
  }

  // TODO: Ulepšati: - centrirati, udaljiti od vrha, staviti jumbotron
  // TODO: Završiti implementaciju
  render() {
    return (
      <div>
        <h1>Welcome!</h1>
        <Form>
          <Form.Group>
            <Form.Label>Username</Form.Label>
            <Form.Control
              onChange={(e) => {
                this.valueInputChange(e);
              }}
              name="username"
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Password</Form.Label>
            <Form.Control
              onChange={(e) => {
                this.valueInputChange(e);
              }}
              name="password"
              as="input"
              type="password"
            ></Form.Control>
          </Form.Group>
          <Button onClick={() => {this.doLogin()}}>Log in</Button>
        </Form>
      </div>
    );
  }
}

export default Login;
