import { Button } from '@mui/material';
import * as XLSX from 'xlsx';
import { ModeOneDto, ModeTwoDto } from '../../services/sessions_service';



export function handleGenerateExcelModeTwo(session: ModeTwoDto) {
    // Create a new workbook
    const workbook = XLSX.utils.book_new();

    // Create a new worksheet
    const worksheet = XLSX.utils.aoa_to_sheet([
        ['TEST ID', 'TEMPERATURE', 'CURRENT', 'VOLTAGE', 'READ DATE TIME', 'RESULT'],
        ...session.results.map((reading) => [
            reading.testId,
            reading.readings[0].temperature,
            reading.readings[0].current,
            reading.readings[0].voltage,
            reading.readings[0].readAt,
            reading.readings[0].result,
        ]),
    ]);



    // Add the worksheet to the workbook
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');

    // Save the workbook as an Excel file
    XLSX.writeFile(workbook, 'mode_two_' + session.defaultConfigurations.sessionId + '.xlsx');
};