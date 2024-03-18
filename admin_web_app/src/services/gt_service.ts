import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { List } from 'reselect/es/types'

export interface TestListResponse {
    status: string
    statusDescription: string
    sessions: List<ModeSeven>
    total: BigInteger
}

export interface ModeSeven {
    createdBy: string
    defaultConfigurations: DefaultConfiguration
    result: SessionResultModeSeven
    status: string
    createdDateTime: string
    lastUpdatedDateTime: string
}

export interface SessionResultModeSeven {
    testId: string
    reading: SessionSevenReading
}

export interface SessionSevenReading {
    macAddress: string
    current: string
    voltage: string
    resistance: string
    result: string
    currentResult: string
    productionOrder: string
    readAt: string
}

export interface DefaultConfiguration {
    customerName: string
    operatorId: string
    serialNo: string
    batchNo: string
    sessionId: string
}

export const gtTestingApi = createApi({
    reducerPath: 'gtTestingApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'http://rvi.v2.exceltch.com/rvi-analyzer-api/',
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("jwt") as string;
            if (!headers.has("Authorization") && token) {
                headers.set("Authorization", `Bearer ${token}`);
            }
            return headers;
        },
    }),
    tagTypes: ['gtTestList'],
    endpoints: (build) => ({
        // getGtTests: build.query<TestListResponse, {}>({
        //     query: () => `rvi/analyzer/v1/session/get/seven`,
        //     providesTags: [{ type: 'gtTestList', id: "getGtTests" }]

        // }),
        getGtTests: build.mutation<TestListResponse, {}>({
            query(data) {
                return {
                    url: `rvi/analyzer/v1/session/get/seven`,
                    method: 'POST',
                    body: {},
                }
            },
            invalidatesTags: [{ type: 'gtTestList', id: "getGtTests" }]
        }),
    }),
})

export const {
    useGetGtTestsMutation,
} = gtTestingApi