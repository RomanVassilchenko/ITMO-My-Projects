import React from 'react';
import ListBoxItem from "./ListBoxItem";

function ListBox({ name, items, selected, onInput }) {

    return (
        <select
            name={name} className="form__list-box"
            onChange={event => onInput(event)}>
            { items.map(item =>
                <ListBoxItem key={item.id} value={item.value}
                             isSelected={item.value === selected}/>
            )}
        </select>
    )
}

export default ListBox