

import React, { useEffect, useState } from "react";

import { apiDelete, apiGet } from "../utils/api";

import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";

const InvoiceIndex = () => {
    const [invoices, setInvoices] = useState([]);
    const [buyerListState, setBuyerList] = useState([]);
    const [sellerListState, setSellerList] = useState([]);
    const [productListState, setProductList] = useState([]);
    const [minPriceState, maxPriceState] = useState(0);
    const [filterState, setFilter] = useState({
        buyerId: undefined,
        sellerIds: undefined,
        product: undefined,
        minPrice: undefined,
        maxPrice: undefined,
        limit: undefined,
    });

    const deleteInvoice = async (id) => {
        try {
            await apiDelete("/api/invoices/" + id);
        } catch (error) {
            console.log(error.message);
            alert(error.message)
        }
        setInvoices(invoices.filter((item) => item._id !== id));
    };

    useEffect(() => {
        apiGet("/api/invoices").then((data) => (setInvoices(data)));
        apiGet('/api/invoices').then((data) => Object.keys(data).map(item => {productListState.push(item.product)}));
        apiGet('/api/persons').then((data) => (setBuyerList(data), setSellerList(data)));
    }, []);



    const handleChange = (e) => {
        if (e.target.value === "false" || e.target.value === "true" || e.target.value === '') {
            setFilter(prevState => {
                return { ...prevState, [e.target.name]: undefined }
            });
        } else {
            setFilter(prevState => {
                return { ...prevState, [e.target.name]: e.target.value }
            });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const params = filterState;
        const data = await apiGet("/api/invoices/", params);
        setInvoices(data);
    };

    return (
        <div>
            <h1>Seznam faktur</h1>
                <InvoiceFilter
                    handleChange={handleChange}
                    handleSubmit={handleSubmit}
                    buyerId={buyerListState}
                    sellerId={sellerListState}
                    product={productListState}
                    filter={filterState}
                    minPrice={minPriceState}
                    maxPrice={maxPriceState}
                    confirm="Filtrovat"
                />
            <InvoiceTable
                deleteInvoice={deleteInvoice}
                items={invoices}
                label="Nalezeno faktur:"
            />
        </div>
    );
};
export default InvoiceIndex;
