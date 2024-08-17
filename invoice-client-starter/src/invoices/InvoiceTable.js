
import React from "react";
import { Link } from "react-router-dom";

const InvoiceTable = ({ label, items, deleteInvoice }) => {

    return (
        <div>
            <p>
                {label} {items.length}
            </p>

            <table className="table table-bordered table-striped">
                <thead className="thead-dark">
                    <tr>
                        <th>#</th>
                        <th>IČ</th>
                        <th>Datum vydání</th>
                        <th>Datum splatnosti</th>
                        <th>Produkt</th>
                        <th>Cena</th>
                        <th>DPH</th>
                        <th>Poznámka</th>
                        <th>Odběratel</th>
                        <th>Dodavatel</th>
                        <th colSpan={3}>Akce</th>
                    </tr>
                </thead>
                <tbody>
                    {items.map((invoice, index) => (
                        <tr key={index + 1}>
                            <td>{index + 1}</td>
                            <td>{invoice.invoiceNumber}</td>
                            <td>{invoice.issued}</td>
                            <td>{invoice.dueDate}</td>
                            <td>{invoice.product}</td>
                            <td>{invoice.price},00 Kč</td>
                            <td>{invoice.vat},00 Kč</td>
                            <td>{invoice.note}</td>
                            <td>{invoice.buyer.name}</td>
                            <td>{invoice.seller.name}</td>
                            <td>
                                <div className="btn-group">
                                    <Link
                                        to={"/invoices/show/" + invoice._id}
                                        className="btn btn-sm btn-info"
                                    >
                                        Zobrazit
                                    </Link>
                                    <Link
                                        to={"/invoices/edit/" + invoice._id}
                                        className="btn btn-sm btn-warning"
                                    >
                                        Upravit
                                    </Link>
                                    <button
                                        onClick={() => deleteInvoice(invoice._id)}
                                        className="btn btn-sm btn-danger"
                                    >
                                        Odstranit
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <Link to={"/invoices/create"} className="btn btn-success">
                Nová faktura
            </Link>
        </div>
    );
};

export default InvoiceTable;
