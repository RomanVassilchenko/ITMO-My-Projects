import {createSlice} from "@reduxjs/toolkit";

const tokenSlice = createSlice({
    name: 'token',
    initialState: localStorage.getItem('token'),
    reducers: {
        updateToken(state, action) {
            return action.payload;
        }
    }
})

export default tokenSlice.reducer
export const {updateToken} = tokenSlice.actions