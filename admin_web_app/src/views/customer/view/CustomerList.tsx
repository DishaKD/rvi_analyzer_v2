import {
  Box,
  Button,
  Card,
  CardActionArea,
  CardContent,
  Container,
  Divider,
  Pagination,
  Paper,
  Table,
  TableBody,
  TableContainer,
  TableHead,
  Typography,
} from "@mui/material";
import { GridColDef } from "@mui/x-data-grid";
import { useGetUsersQuery, User } from "../../../services/user_service";
import {
  useGetCustomerQuery,
  Customer,
  useDeleteCustomerMutation,
} from "../../../services/customer_service";
import { StyledTableCell, StyledTableRow } from "../../mode_one/mode-one-list";
import CustomizedMenusUsers from "../../user/view/custom-menu-user";
import { List } from "reselect/es/types";
import DeleteIcon from "@mui/icons-material/Delete";
import React, { useEffect, useState } from "react";
import SessionTimeoutPopup from "../../components/session_logout";
import AddIcon from "@mui/icons-material/Add";
import GroupAddIcon from "@mui/icons-material/GroupAdd";
import { AddUserModel } from "../../user/view/add-user";
import { AddCustomerModel } from "../add/add-customer";
import { AllocateStyleModel } from "../add/allocate-style";

const columns: GridColDef[] = [
  { field: "customer", headerName: "Customer", width: 250 },
  { field: "createdBy", headerName: "Created by", width: 250 },
  { field: "createdDate", headerName: "Created date & time", width: 250 },
  { field: "action", headerName: "Action", width: 250 },
];

export default function CustomerList() {
  const { data, error, isLoading } = useGetCustomerQuery("");
  const [deleteCustomer] = useDeleteCustomerMutation();
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [page, setPage] = useState(1);
  const [customersList, setCUstomersList] = useState<List<Customer>>([]);

  const startIndex = (page - 1) * rowsPerPage;
  const endIndex = startIndex + rowsPerPage;
  const paginatedCustomers = customersList.slice(startIndex, endIndex);

  useEffect(() => {
    if (data?.customers) {
      setCUstomersList(data?.customers);
    }
  }, [data]);

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const [open, setOpen] = useState(false);
  const [openStyle, setOpenStyle] = useState(false);
  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error != null && "status" in error) {
    if (error.status == 401 && error.data == null) {
      return <SessionTimeoutPopup />;
    } else {
      return <></>;
    }
  } else {
    return (
      <>
        {isLoading ? (
          isLoading
        ) : (
          <Box
            component="main"
            sx={{
              flexGrow: 1,
            }}
          >
            <Container maxWidth={false}>
              <>
                <Box m="0px 0 0 0" sx={{}}>
                  <Card
                    sx={{
                      maxWidth: 1600,
                      maxHeight: "80vh",
                      backgroundColor: "#FFFFFF",
                      boxShadow: "1px 1px 10px 10px #e8e8e8",
                    }}
                  >
                    <CardActionArea>
                      <CardContent sx={{}}>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="div"
                          color="grey"
                        >
                          Customers
                        </Typography>
                        <Container
                          sx={{
                            display: "flex",
                            flexDirection: "row-reverse",
                            padding: "0",
                          }}
                        >
                          <Box display="flex" justifyContent="flex-end">
                            <Button
                              variant="contained"
                              startIcon={<GroupAddIcon />}
                              sx={{
                                backgroundColor: "#ab47bc",
                                "&:hover": { backgroundColor: "#7b1fa2" },
                              }}
                              onClick={() => setOpenStyle(true)}
                            >
                              ALLOCATE CUSTOMERS
                            </Button>
                          </Box>
                          <Box display="flex" justifyContent="flex-end">
                            <Button
                              variant="contained"
                              startIcon={<AddIcon />}
                              sx={{
                                backgroundColor: "#00e676",
                                mx: 1,
                                "&:hover": { backgroundColor: "#00a152" },
                              }}
                              onClick={() => setOpen(true)}
                            >
                              ADD
                            </Button>
                          </Box>
                        </Container>
                        <Divider
                          sx={{
                            borderColor: "grey",
                            my: 1,
                            borderStyle: "dashed",
                          }}
                        />
                        <Divider
                          sx={{
                            borderColor: "grey",
                            my: 1,
                            borderStyle: "dashed",
                          }}
                        />
                        <Paper sx={{ width: "100%", overflow: "hidden" }}>
                          <TableContainer sx={{ maxHeight: "100%" }}>
                            <Table stickyHeader aria-label="sticky table">
                              <TableHead sx={{ backgroundColor: "#9e9e9e" }}>
                                <StyledTableRow>
                                  {columns.map((column) => (
                                    <StyledTableCell
                                      key={column.headerName}
                                      align={column.align}
                                      style={{ maxWidth: column.width }}
                                    >
                                      {column.headerName}
                                    </StyledTableCell>
                                  ))}
                                </StyledTableRow>
                              </TableHead>
                              <TableBody>
                                {paginatedCustomers
                                  .map((customer: Customer, index: any) => {
                                    return (
                                      <StyledTableRow
                                        key={index}
                                        hover
                                        role="checkbox"
                                        tabIndex={-1}
                                      >
                                        <StyledTableCell align={"left"}>
                                          {customer.name}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {customer.createdBy}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {customer.createdDateTime}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          <Box
                                            display="flex"
                                            justifyContent="flex-center"
                                          >
                                            <Button
                                              variant="contained"
                                              startIcon={<DeleteIcon />}
                                              sx={{
                                                backgroundColor: "#f50057",
                                                mx: 1,
                                                "&:hover": {
                                                  backgroundColor: "#ab003c",
                                                },
                                              }}
                                              onClick={() => {
                                                deleteCustomer({
                                                  name: customer.name,
                                                })
                                                  .unwrap()
                                                  .then((payload) => {
                                                    if (
                                                      payload.status == "S1000"
                                                    ) {
                                                      // setOpenSuccess(true)
                                                      console.log(
                                                        "customer deleted"
                                                      );
                                                    }
                                                  })
                                                  .catch((error) => {
                                                    // setOpenFail(true)
                                                    console.log("failed");
                                                  });
                                              }}
                                            >
                                              Delete
                                            </Button>
                                          </Box>
                                        </StyledTableCell>
                                      </StyledTableRow>
                                    );
                                  })
                                  .reverse()}
                              </TableBody>
                            </Table>
                          </TableContainer>
                        </Paper>
                        <Box display="flex" justifyContent="flex-end">
                          <Pagination
                            count={Math.ceil(
                              customersList.length / rowsPerPage
                            )}
                            sx={{ mt: 2 }}
                            variant="outlined"
                            shape="rounded"
                            page={page}
                            onChange={handleChange}
                          />
                        </Box>
                      </CardContent>
                    </CardActionArea>
                  </Card>
                </Box>
              </>
            </Container>
            <AddCustomerModel open={open} changeOpenStatus={setOpen} />
            <AllocateStyleModel
              open={openStyle}
              changeOpenStatus={setOpenStyle}
            />
          </Box>
        )}
      </>
    );
  }
}
