import React, { useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import axios from "axios";


const Ordertable = () => {
  const get_data_url = "http://localhost:9200/student/fetchOrders";
  const [data, setdata] = useState([]);

  useEffect(() => {
    axios
      .post(get_data_url, {
        rollNum: JSON.parse(localStorage.getItem("user-info"))["uname"],
      })
      .then(function (response) {
        setdata(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  return (
    <div>
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
                <TableCell align="right">{row.collectedByRollNum}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default Ordertable;
