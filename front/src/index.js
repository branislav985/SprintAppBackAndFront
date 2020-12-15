import React from "react";
import ReactDOM from "react-dom";
import {
  Route,
  Link,
  HashRouter as Router,
  Switch,
  Redirect,
} from "react-router-dom";
import Home from "./components/Home";
import NotFound from "./components/NotFound";
import { Container, Navbar, Nav, Button, Form } from "react-bootstrap";
import Login from "./components/authentication/Login";
import { logout } from "./services/auth";
import Zadaci from "./components/zadaci/Zadaci";
import EditZadatak from "./components/zadaci/EditZadatak";

class App extends React.Component {
  render() {
    let token = window.localStorage.getItem("token");

    if (token) {
      return (
        <div>
          <Router>
            <Navbar bg="dark" variant="dark" expand>
              <Navbar.Brand as={Link} to="/">
                JWD
              </Navbar.Brand>
              <Nav className="mr-auto">
                <Nav.Link as={Link} to="/zadaci">
                  Zadaci
                </Nav.Link>
              </Nav>

              <Button
                onClick={() => {
                  logout();
                }}
                variant="outline-info"
              >
                Log Out
              </Button>
            </Navbar>

            <Container style={{marginTop:25}}>
              <Switch>
                <Route exact path="/" component={Home} />
                <Route exact path="/zadaci" component={Zadaci} />
                <Route exact path="/zadaci/edit/:id" component={EditZadatak} />
                <Route exact path="/login">
                  <Redirect to="/"></Redirect>
                </Route>
                <Route component={NotFound} />
              </Switch>
            </Container>
          </Router>
        </div>
      );
    } else {
      return (
        <Router>
          <Container>
            <Switch>
              <Route exact path="/login" component={Login}></Route>
              <Route path="/">
                <Redirect to="/login"></Redirect>
              </Route>
            </Switch>
          </Container>
        </Router>
      );
    }
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
