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
import { StyledTableCell, StyledTableRow } from "../../mode_one/mode-one-list";
import CustomizedMenusUsers from "../../user/view/custom-menu-user";
import { List } from "reselect/es/types";
import React, { useEffect, useState } from "react";
import SessionTimeoutPopup from "../../components/session_logout";
import AddIcon from "@mui/icons-material/Add";
import { AddTestModel } from "../add/add-test";
import { Style, useGetStyleQuery } from "../../../services/styles_service";
import {
  ParameterMode,
  Test,
  useGetTestQuery,
} from "../../../services/test_service";

const columns: GridColDef[] = [
  {
    field: "testGate",
    headerName: "Test Gate",
    width: 200,
  },
  {
    field: "parameterModes",
    headerName: "Parameter Modes",
    width: 200,
  },
  {
    field: "parameters",
    headerName: "Parameters",
    width: 200,
  },
  {
    field: "material",
    headerName: "Material",
    width: 200,
  },
  {
    field: "createdBy",
    headerName: "Created By",
    width: 180,
  },
  {
    field: "createdDateTime",
    headerName: "Created Date",
    width: 250,
  },
];

export default function TestList() {
  var { data, error, isLoading } = useGetTestQuery("");
  var userRoles: string | string[] = [];
  const [open, setOpen] = useState(false);
  var admin = "";
  var roles = localStorage.getItem("roles");
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [page, setPage] = useState(1);
  const [testsList, setTestsList] = useState<List<Test>>([]);

  const startIndex = (page - 1) * rowsPerPage;
  const endIndex = startIndex + rowsPerPage;
  const paginatedTests = testsList.slice(startIndex, endIndex);

  useEffect(() => {
    console.log(paginatedTests);
  }, [page]);

  useEffect(() => {
    if (data?.tests) {
      setTestsList(data.tests);
    }
  }, [data]);

  if (roles === null) {
    admin = "ADMIN";
    console.log("roles empty");
  } else {
    userRoles = roles.split(",").map((item) => item.trim());
    if (
      userRoles.includes("CREATE_TOP_ADMIN") &&
      userRoles.includes("CREATE_ADMIN")
    ) {
      admin = "TOP_ADMIN";
    } else if (userRoles.includes("CREATE_USER")) {
      admin = "ADMIN";
    }
  }

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

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
                          Tests
                        </Typography>
                        <Box display="flex" justifyContent="flex-end">
                          <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            sx={{
                              backgroundColor: "#00e676",
                              "&:hover": { backgroundColor: "#00a152" },
                            }}
                            onClick={() => setOpen(true)}
                          >
                            ADD
                          </Button>
                        </Box>
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
                          <TableContainer sx={{ maxHeight: 300 }}>
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
                                {paginatedTests
                                  .map((item: Test, index: any) => {
                                    return (
                                      <StyledTableRow
                                        key={index}
                                        hover
                                        role="checkbox"
                                        tabIndex={-1}
                                      >
                                        <StyledTableCell align={"left"}>
                                          {item.testGate}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.parameterModes.map(
                                            (object: ParameterMode) => {
                                              return (
                                                <Typography>
                                                  {object.name}
                                                </Typography>
                                              );
                                            }
                                          )}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.parameterModes.map(
                                            (object: ParameterMode) => {
                                              return (
                                                <Typography>
                                                  {object.parameter}
                                                </Typography>
                                              );
                                            }
                                          )}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.material}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.createdBy}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.createdDateTime}
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
                            count={Math.ceil(testsList.length / rowsPerPage)}
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
            <AddTestModel open={open} changeOpenStatus={setOpen} />
          </Box>
        )}
      </>
    );
  }
}
