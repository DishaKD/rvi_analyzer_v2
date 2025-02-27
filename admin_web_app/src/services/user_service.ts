import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { List } from 'reselect/es/types'
import { CommonResponse, GeneralResponse } from './device_service'
import { RolesResponse } from './login_service'

export interface UserListResponse {
    status: string
    statusDescription: string
    users: List<User>
}

export interface PasswordResetResponse {
    status : string
    statusDescription: string
}

export interface User {
    username: string
    group: string
    status: string
    supervisor: string
    passwordType: string
    createdBy: string
    createdDateTime: string
    lastUpdatedDateTime: boolean
}

export const userApi = createApi({
    reducerPath: 'userApi',
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
    tagTypes: ['userList'],
    endpoints: (build) => ({
        getUsers: build.query<UserListResponse, {}>({
            query: () => `rvi/analyzer/v1/users`,
            providesTags: [{ type: 'userList', id: "getUsers" }]

        }),
        updateUser: build.mutation<CommonResponse, {}>({
            query(body) {
                return {
                    url: `rvi/analyzer/v1/user/update`,
                    method: 'POST',
                    body: body,
                }
            },
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        resetPassword: build.mutation<UserListResponse, { username: string }>({
            query: (data) => `rvi/analyzer/v1/user/resetPassword/${data.username}`,
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        resetDefaultPassword: build.mutation<PasswordResetResponse, {}>({
            query(body) {
                return {
                    url: `rvi/analyzer/v1/user/resetPassword`,
                    method: 'POST',
                    body,
            };},
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        addUser: build.mutation<CommonResponse, {}>({
            query(body) {
                return {
                    url: `register/user`,
                    method: 'POST',
                    body: body,
                }
            },
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        getRoles: build.mutation<RolesResponse, {}>({
            query() {
                return {
                    url: `rvi/analyzer/v1/user/roles`,
                    // url: `login/user`,
                    method: 'GET'
                }
            },
        }),
    }),
})

export const {
    useGetUsersQuery,
    useUpdateUserMutation,
    useResetPasswordMutation,
    useResetDefaultPasswordMutation,
    useAddUserMutation,
    useGetRolesMutation
} = userApi