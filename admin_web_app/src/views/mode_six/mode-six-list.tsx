import { Box, Card, CardActionArea, CardContent, Container, Divider, Pagination, Paper, Table, TableBody, TableContainer, TableHead, Typography } from "@mui/material";
import { GridColDef } from '@mui/x-data-grid';
import { ModeSixDto, useGetModeSixSessionsQuery } from "../../services/sessions_service";
import React from "react";
import SessionTimeoutPopup from "../components/session_logout";
import TableSearchForm from "../components/table_search_form";
import { StyledTableCell, StyledTableRow } from "../mode_one/mode-one-list";
import CustomizedMenusModeSix from "./custom-menu-mode-six";


const columns: GridColDef[] = [
    {
        field: 'sessionId', headerName: 'Session Id', width: 150,
    },
    {
        field: 'customerName',
        headerName: 'Customer name',
        width: 120,
    },
    {
        field: 'operatorId',
        headerName: 'Operator ID',
        width: 100,
    },
    {
        field: 'serialNo',
        headerName: 'Serial NO',
        width: 100,
    },
    {
        field: 'batchNo',
        headerName: 'Batch NO',
        width: 100,
    },
    {
        field: 'fixedCurrent',
        headerName: 'Fixed Current',
        width: 150,
    },
    {
        field: 'maxVoltage',
        headerName: 'Max Voltage',
        width: 150,
    },
    {
        field: 'timeDuration',
        headerName: 'Time Duration',
        width: 120,
    },
    {
        field: 'actions',
        headerName: 'Actions',
        type: 'actions',
        width: 100,
    },
];

export default function ModeSixList() {
    const [date, setDate] = React.useState<Date | null>(null);
    const [filterType, setFilterType] = React.useState("CREATED_BY");
    const [filterValue, setFilterValue] = React.useState("");
    const [pageCount, setPageCount] = React.useState(1);
    const [page, setPage] = React.useState(1);

    var { data, error, isLoading } = useGetModeSixSessionsQuery({ data: { date: date, filterType: filterType, filterValue: filterValue }, page: page })

    const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    function setSearchParams(date: Date | null, filterType: string, filterValue: string) {
        setFilterType(filterType);
        setFilterValue(filterValue);
        setDate(date)
        setPage(1)
    }

    React.useEffect(() => {
        if (data?.sessions != null) {
            setPageCount(Math.trunc((data.total + 15 - 1) / 15))
        }
    }, [data])

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (error != null && 'status' in error) {
        if (error.status == 401 && error.data == null) {
            return <SessionTimeoutPopup />
        }
        else {
            return <></>
        }
    } else {
        return (
            <>
                {isLoading ? "Loading" :
                    <Box
                        component="main"
                        sx={{
                            flexGrow: 1,
                        }}
                    >

                        <Container maxWidth={false}>
                            <>
                                <Box
                                    m="0px 0 0 0"
                                    sx={{}}
                                >
                                    <Card sx={{ maxWidth: 1600, backgroundColor: "#FFFFFF", boxShadow: "1px 1px 10px 10px #e8e8e8" }}>
                                        <CardActionArea>

                                            <CardContent sx={{}}>
                                                <Typography gutterBottom variant="h5" component="div" color="grey">
                                                    Mode Six
                                                </Typography>
                                                <Divider
                                                    sx={{
                                                        borderColor: 'grey',
                                                        my: 1.5,
                                                        borderStyle: 'dashed'
                                                    }}
                                                />
                                                <TableSearchForm searchFun={setSearchParams}></TableSearchForm>
                                                <Divider
                                                    sx={{
                                                        borderColor: 'grey',
                                                        my: 1.5,
                                                        borderStyle: 'dashed'
                                                    }}
                                                />
                                                <Paper sx={{ width: '100%', overflow: 'hidden' }}>
                                                    <TableContainer sx={{ maxHeight: 440 }}>
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
                                                                {data!.sessions
                                                                    .map((item, index) => {
                                                                        return (
                                                                            <StyledTableRow hover role="checkbox" tabIndex={-1} >
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.defaultConfigurations.sessionId}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.defaultConfigurations.customerName}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.defaultConfigurations.operatorId}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.defaultConfigurations.serialNo}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.defaultConfigurations.batchNo}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.sessionConfigurationModeSix.fixedCurrent}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.sessionConfigurationModeSix.maxVoltage}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'left'}>
                                                                                    {item.sessionConfigurationModeSix.timeDuration}
                                                                                </StyledTableCell>
                                                                                <StyledTableCell align={'right'}>
                                                                                    <CustomizedMenusModeSix session={item as ModeSixDto} />
                                                                                </StyledTableCell>
                                                                            </StyledTableRow>
                                                                        );
                                                                    })}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                </Paper>
                                                {pageCount != 0 ?
                                                    <Box display="flex" justifyContent="flex-end">
                                                        <Pagination count={pageCount} sx={{ mt: 2 }} variant="outlined" shape="rounded" page={page} onChange={handleChange} />
                                                    </Box>
                                                    : <></>
                                                }
                                            </CardContent>
                                        </CardActionArea>
                                    </Card>

                                </Box>
                            </>
                        </Container>
                    </Box>
                }

            </>
        );
    }
}
