import React from "react";
import { Table, Button, Form, ButtonGroup } from "react-bootstrap";

import SprintsAxios from "../../apis/SprintsAxios";

class Zadaci extends React.Component {
  constructor(props) {
    super(props);

    let zadatak = {
      ime: "",
      zaduzeni: "",
      bodovi: 0,
      sprintId: -1,
    };

    this.state = {
      zadatak: zadatak,
      zadaci: [],
      sprintovi: [],
      search: { imeZadatka: "", idSprinta: -1 },
      pageNum: 0,
      totalPages: 1,
    };
  }

  componentDidMount() {
    this.getData();
  }

  async getData() {
    await this.getSprintovi();
    await this.getZadaci();
  }

  async getZadaci(page = null) {
    let config = { params: {} };

    if (this.state.search.imeZadatka != "") {
      config.params.imeZadatka = this.state.search.imeZadatka;
    }

    if (this.state.search.idSprinta != -1) {
      config.params.idSprinta = this.state.search.idSprinta;
    }

    if (page != null) {
      config.params.pageNum = page;
    } else {
      config.params.pageNum = this.state.pageNum;
    }

    try {
      let result = await SprintsAxios.get("/zadaci", config);
      if (result && result.status === 200) {
        this.setState({
          zadaci: result.data,
          totalPages: result.headers["total-pages"],
        });
      }
    } catch (error) {
      alert("Nije uspelo dobavljanje.");
    }
  }

  async getSprintovi() {
    try {
      let result = await SprintsAxios.get("/sprintovi");
      if (result && result.status === 200) {
        this.setState({
          sprintovi: result.data,
        });
      }
    } catch (error) {
      alert("Nije uspelo dobavljanje.");
    }
  }

  goToEdit(zadatakId) {
    this.props.history.push("/zadaci/edit/" + zadatakId);
  }

  async doAdd() {
    try {
      await SprintsAxios.post("/zadaci/", this.state.zadatak);

      let zadatak = {
        ime: "",
        zaduzeni: "",
        bodovi: 0,
        stanjeId: -1,
        sprintId: -1,
      };

      this.setState({ zadatak: zadatak });

      this.getZadaci();
    } catch (error) {
      alert("Nije uspelo dodavanje.");
    }
  }

  async doDelete(zadatakId) {
    try {
      await SprintsAxios.delete("/zadaci/" + zadatakId);
      this.getZadaci();
    } catch (error) {
      alert("Nije uspelo brisanje.");
    }
  }

  addValueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let zadatak = this.state.zadatak;
    zadatak[name] = value;

    this.setState({ zadatak: zadatak });
  }

  searchValueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let search = this.state.search;
    search[name] = value;

    this.setState({ search: search });
  }

  doSearch() {
    this.setState({ totalPages: 1, pageNum: 0 });
    this.getZadaci(0);
  }

  async prelazak(id) {
    try {
      await SprintsAxios.post(`/zadaci/${id}/prelazak`);
      this.getZadaci();
    } catch (error) {
      alert("Nije moguće promeniti stanje.");
    }
  }

  changePage(direction) {
    let page = this.state.pageNum + direction;
    this.getZadaci(page);
    this.setState({ pageNum: page });
    //this.setState({pageNum: page}, this.getZadaci);
  }

  render() {
    return (
      <div>
        <h1>Zadaci</h1>

        <Form>
          <Form.Group>
            <Form.Label>Ime</Form.Label>
            <Form.Control
              onChange={(event) => this.addValueInputChange(event)}
              name="ime"
              value={this.state.zadatak.ime}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Zaduženi</Form.Label>
            <Form.Control
              onChange={(event) => this.addValueInputChange(event)}
              name="zaduzeni"
              value={this.state.zadatak.zaduzeni}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Bodovi</Form.Label>
            <Form.Control
              onChange={(event) => this.addValueInputChange(event)}
              name="bodovi"
              value={this.state.zadatak.bodovi}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Sprint</Form.Label>
            <Form.Control
              onChange={(event) => this.addValueInputChange(event)}
              name="sprintId"
              value={this.state.zadatak.sprintId}
              as="select"
            >
              <option value={-1}></option>
              {this.state.sprintovi.map((sprint) => {
                return (
                  <option value={sprint.id} key={sprint.id}>
                    {sprint.ime}
                  </option>
                );
              })}
            </Form.Control>
          </Form.Group>
          <Button variant="primary" onClick={() => this.doAdd()}>
            Add
          </Button>
        </Form>

        <Form style={{marginTop:35}}>
          <Form.Group>
            <Form.Label>Ime zadatka</Form.Label>
            <Form.Control
              value={this.state.search.imeZadatka}
              name="imeZadatka"
              as="input"
              onChange={(e) => this.searchValueInputChange(e)}
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Sprint</Form.Label>
            <Form.Control
              onChange={(event) => this.searchValueInputChange(event)}
              name="idSprinta"
              value={this.state.search.idSprinta}
              as="select"
            >
              <option value={-1}></option>
              {this.state.sprintovi.map((sprint) => {
                return (
                  <option value={sprint.id} key={sprint.id}>
                    {sprint.ime}
                  </option>
                );
              })}
            </Form.Control>
          </Form.Group>
          <Button onClick={() => this.doSearch()}>Pretraga</Button>
        </Form>

        <ButtonGroup style={{ marginTop: 25 }}>
          <Button
            disabled={this.state.pageNum == 0}
            onClick={() => this.changePage(-1)}
          >
            Prethodna
          </Button>
          <Button
            disabled={this.state.pageNum + 1 == this.state.totalPages}
            onClick={() => this.changePage(1)}
          >
            Sledeća
          </Button>
        </ButtonGroup>

        <Table bordered striped style={{ marginTop: 5 }}>
          <thead className="thead-dark">
            <tr>
              <th>Ime</th>
              <th>Zaduženi</th>
              <th>Bodovi</th>
              <th>Stanje</th>
              <th>Sprint</th>
              <th colSpan={2}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {this.state.zadaci.map((zadatak) => {
              return (
                <tr key={zadatak.id}>
                  <td>{zadatak.ime}</td>
                  <td>{zadatak.zaduzeni}</td>
                  <td>{zadatak.bodovi}</td>
                  <td>{zadatak.stanjeIme}</td>
                  <td>{zadatak.sprintIme}</td>
                  <td>
                    <Button
                      disabled={zadatak.stanjeId === 3}
                      variant="info"
                      onClick={() => this.prelazak(zadatak.id)}
                    >
                      Prelazak
                    </Button>

                    <Button
                      variant="warning"
                      onClick={() => this.goToEdit(zadatak.id)}
                      style={{ marginLeft: 5 }}
                    >
                      Edit
                    </Button>

                    <Button
                      variant="danger"
                      onClick={() => this.doDelete(zadatak.id)}
                      style={{ marginLeft: 5 }}
                    >
                      Delete
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      </div>
    );
  }
}

export default Zadaci;
