import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export interface LoginResponse {
    state: string
    stateDescription: string
    user: SimpleUser
    jwt: string
}

export interface SimpleUser {
    userName: string
    type: string
    status: string
    createdBy: string
    createdDateTime: string
}

export const loginApi = createApi({
    reducerPath: 'loginApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://127.0.0.1:7550/' }),
    tagTypes: ['loginRequest'],
    endpoints: (build) => ({
        login: build.mutation<LoginResponse, {}>({
            query(body) {
                return {
                    url: `login/admin`,
                    method: 'POST',
                    body,
                }
            },
        }),
    }),
})

export const {
    useLoginMutation,
} = loginApi