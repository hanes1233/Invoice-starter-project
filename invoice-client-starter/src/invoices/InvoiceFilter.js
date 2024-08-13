import React from 'react';
import InputSelect from '../components/InputSelect';
import InputField from '../components/InputField';

const InvoiceFilter = (props) => {

    const handleChange = (e) => {
        props.handleChange(e);
    };

    const handleSubmit = (e) => {
        props.handleSubmit(e);
    };

    const filter = props.filter;

    return (
        <form onSubmit={handleSubmit}>
            <div className="row">
                <div className="col">
                    <InputSelect
                        name="sellerList"
                        items={props.sellerId}
                        handleChange={(e) => {
                            filter.sellerId = e.target.value;
                        }}
                        label="Dodavatel"
                        prompt="nevybrán"
                        value={filter.sellerId}
                    />
                </div>
                <div className="col">
                    <InputSelect
                        name="buyerList"
                        items={props.buyerId}
                        handleChange={(e) => {
                            filter.buyerId = e.target.value;
                        }}
                        label="Odběratel"
                        prompt="nevybrán"
                        value={filter.buyerId}
                    />
                </div>
                <div className="col">
                    <InputField
                        type="text"
                        name="product"
                        handleChange={handleChange}
                        label="Produkt"
                        prompt="nevybrán"
                        value={filter.product}
                    />
                </div>
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="minPrice"
                        handleChange={handleChange}
                        label="Cena od"
                        prompt="0"
                        value={filter.minPrice}
                    />
                </div>
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="maxPrice"
                        handleChange={handleChange}
                        label="Cena do"
                        prompt="0"
                        value={filter.maxPrice}
                    />
                </div>
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="limit"
                        handleChange={handleChange}
                        label="Limit počtu faktur"
                        prompt="neuveden"
                        value={filter.limit ? filter.limit : ''}
                    />
                </div>
            </div>
            <div className="row my-3">
                <div className="col-1">
                    <input
                        type="submit"
                        className="btn btn-primary float-right mt-2"
                        value={props.confirm}
                    />
                </div>
                <div className="col-1">
                    <input
                        type="reset"
                        className="btn btn-secondary float-right mt-2"
                        value='Reset'
                        onClick={() => {
                            location.reload();
                        }}
                    />
                </div>
            </div>
        </form>
    );
};

export default InvoiceFilter;