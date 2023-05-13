import React, { useState, useEffect } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import moment from "moment";
import TextField from "@mui/material/TextField";
import { Button } from "@material-ui/core";
import axios from "axios";

// import data from "./mock_data.json";

const initialState = {
  orderId: "",
  rollNum: "",
  deliveryFrom: "",
  deliveryDate: moment().format("D/MM/YYYY"),
  deliveryTime: moment().format("HH:mm"),
  collectedByRollNum: null,
};

const collectState = {
  orderId: "",
  collectedByRollNum: "",
};

const Ordertable = () => {
  const add_order_url = "http://192.168.49.2:30164/security/addOrder";
  const get_data_url = "http://192.168.49.2:30164/security/fetchOrders";
  const collect_order_url = "http://192.168.49.2:30164/security/collectedOrder";

  const [data, setData] = useState([]);
  const [formData, setFormData] = useState(initialState);
  const [collectData, setcollectData] = useState(collectState);

  const btnstyle = {
    backgroundColor: "#20CD51",
    margin: "1.5%",
    width: "150px",
  };

  const collectbtnstyle = {
    backgroundColor: "#3455eb",
    margin: "1.5%",
    width: "150px",
  };

  useEffect(() => {
    axios
      .post(get_data_url, {})
      .then(function (response) {
        setData(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post(add_order_url, formData)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const handleCollectChange = (e, orderid) => {
    setcollectData({
      ...collectData,
      [e.target.name]: e.target.value,
      orderId: orderid,
    });
  };

  const handleCollectClick = (e) => {
    e.preventDefault();
    axios
      .post(collect_order_url, collectData)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          name="orderId"
          type="text"
          label="Order ID"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <TextField
          name="rollnumber"
          type="text"
          label="Roll Number"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <TextField
          name="deliveryFrom"
          type="text"
          label="Delivered From"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <Button
          type="submit"
          color="primary"
          variant="contained"
          style={btnstyle}
        >
          Submit
        </Button>
      </form>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>
                <h3>Order ID</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Roll Number</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered From</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered Date</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered Time</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Collected By</h3>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row) => (
              <TableRow
                key={row.orderId}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.orderId}
                </TableCell>
                <TableCell align="right">{row.rollNum}</TableCell>
                <TableCell align="right">{row.deliveryFrom}</TableCell>
                <TableCell align="right">{row.deliveryDate}</TableCell>
                <TableCell align="right">{row.deliveryTime}</TableCell>
                <TableCell align="right">
                  <TextField
                    name="collectedByRollNum"
                    type="text"
                    label="Roll Number"
                    variant="outlined"
                    defaultValue={row.collectedByRollNum}
                    onChange={(event) =>
                      handleCollectChange(event, row.orderId)
                    }
                    sx={{ margin: "1%" }}
                  />
                </TableCell>
                <TableCell align="right">
                  <Button
                    color="primary"
                    variant="contained"
                    style={collectbtnstyle}
                    onClick={handleCollectClick}
                  >
                    Collect
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default Ordertable;
