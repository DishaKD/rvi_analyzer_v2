import { Box, Button, Container } from "@mui/material";
import { blue, green, grey } from "@mui/material/colors";
import { DataGrid, GridColDef, GridToolbar, GridValueGetterParams } from '@mui/x-data-grid';
import { ModeFiveDto, ModeFourDto, useGetModeFiveSessionsQuery, useGetModeFourSessionsQuery } from "../../services/sessions_service";
import { ModeFiveSingleView } from './mode-five-single-view'
import React from "react";
import SessionTimeoutPopup from "../components/session_logout";



const columns: GridColDef[] = [
    {
        field: 'sessionId', headerName: 'Session Id', width: 150, valueGetter: (params: GridValueGetterParams) =>
            `${params.row.defaultConfigurations.sessionId}`,
    },
    {
        field: 'customerName',
        headerName: 'Customer name',
        width: 120,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.defaultConfigurations.customerName}`,
    },
    {
        field: 'operatorId',
        headerName: 'Operator ID',
        width: 100,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.defaultConfigurations.operatorId}`
    },


    {
        field: 'batchNo',
        headerName: 'Batch NO',
        width: 100,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.defaultConfigurations.batchNo}`
    },
    {
        field: 'fixedVoltage',
        headerName: 'Fixed Voltage',
        width: 150,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.sessionConfigurationModeFive.fixedVoltage}`
    },
    {
        field: 'maxCurrent',
        headerName: 'Max Current',
        width: 150,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.sessionConfigurationModeFive.maxCurrent}`
    },
    {
        field: 'timeDuration',
        headerName: 'Time Duration',
        width: 120,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.row.sessionConfigurationModeFive.timeDuration}`
    },
    {
        field: 'actions',
        headerName: 'Actions',
        type: 'actions',
        width: 100,
        renderCell: (params) => (
            <ModeFiveSingleView session={params.row as ModeFiveDto} />
        ),
    },
];

export default function ModeFiveList() {
    const [date, setDate] = React.useState<Date | null>(null);
    const [filterType, setFilterType] = React.useState("CREATED_BY");
    const [filterValue, setFilterValue] = React.useState("");
    const [pageCount, setPageCount] = React.useState(1);
    const [page, setPage] = React.useState(1);

    var { data, error, isLoading } = useGetModeFiveSessionsQuery({})

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
            setPageCount((data.sessions.length + 20 - 1) / 20)
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
                                    m="20px 0 0 0"
                                    height="75vh"
                                    sx={{
                                        "& .MuiDataGrid-root": {
                                        },
                                        "& .MuiDataGrid-cell": {
                                        },
                                        "& .name-column--cell": {
                                            color: blue[300],
                                        },
                                        "& .MuiDataGrid-columnHeaders": {
                                            backgroundColor: '#1999ff',
                                        },
                                        "& .MuiDataGrid-virtualScroller": {
                                            backgroundColor: grey[200],
                                        },
                                        "& .MuiDataGrid-footerContainer": {
                                            backgroundColor: '#1999ff',
                                        },
                                        "& .MuiCheckbox-root": {
                                            color: `${green[200]} !important`,
                                        },
                                    }}
                                >
                                    <DataGrid
                                        rows={data!.sessions.map((item, index) => ({ id: index + 1, ...item }))}
                                        columns={columns}
                                        pageSize={100}
                                        rowsPerPageOptions={[100]}
                                        disableSelectionOnClick
                                        experimentalFeatures={{ newEditingApi: true }}
                                        components={{
                                            Toolbar: GridToolbar,
                                        }}
                                    />
                                </Box>
                                {console.log(data?.sessions)}
                            </>
                        </Container>
                    </Box>
                }

            </>
        );
    }
}
