package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_md_buyer_tax_type", schema = "conf")
@Data
public class TblMdBuyerTaxTypeEnitity {

    @Id
    @Column(name = "buyer_tax_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_tax_type_seq")
    @SequenceGenerator(
            name = "buyer_tax_type_seq",
            sequenceName = "buyer_tax_type_seq",
            allocationSize = 1)
    private Integer buyerTaxTypeId;

    @Basic
    @Column(name = "buyer_tax_type_code")
    private String buyerTaxTypeCode;

    @Basic
    @Column(name = "buyer_tax_type_name")
    private String buyerTaxTypeName;

    @Basic
    @Column(name = "record_status")
    private String recordStatus;

}
