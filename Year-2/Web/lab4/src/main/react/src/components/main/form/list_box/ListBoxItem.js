import React from 'react';

function ListBoxItem({value, isSelected = false}) {
    return (
        <option className="form__list-box-item" value={value} selected={isSelected}>
            {value}
        </option>
    )
}

export default ListBoxItem