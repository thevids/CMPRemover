package no.moller.carad.carweb.transfer.dao;

import java.util.List;

import javax.annotation.Nullable;

import no.moller.carad.carweb.transfer.domain.UsedCarStockTransfer;
import no.moller.carad.carweb.transfer.rowmapper.UsedCarStockTransferMapper;
import no.moller.commons.dao.BaseDao;
import no.moller.commons.dao.DaoHelper;
import no.moller.commons.param.impl.CountryCode;
import no.moller.commons.param.impl.DealerNumber;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Dao for tabellen UsedCarStockTransfer.
 */
public class UsedCarStockTransferDao extends BaseDao {

    private static final String READ_USED_CAR_STOCK_TRANSFER =
        " SELECT a." + UsedCarStockTransfer.PARAM_TO_DEALER_NO
        +  ",    a." + UsedCarStockTransfer.PARAM_COUNTRY_CODE
        +  ",    a." + UsedCarStockTransfer.PARAM_DEALER_NO
            + " FROM MWIN." + UsedCarStockTransfer.TABLE_NAME + " a";

    private static final String PRIMARY_KEY_CLAUSE =
        "   WHERE a.COUNTRY_CODE = :" + UsedCarStockTransfer.PARAM_COUNTRY_CODE
        +  " AND  a.DEALER_NO =:" + UsedCarStockTransfer.PARAM_DEALER_NO;

    /**
     * Reads a row, find by primary key.
     *
     * @param countryCode
     *            the country code
     * @param dealerNumber
     *            the dealerNumber
     * @return the valueObject or null if one does not exist
     */
    @Nullable
    public UsedCarStockTransfer read(
        final CountryCode countryCode,
        final DealerNumber dealerNumber) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(UsedCarStockTransfer.PARAM_COUNTRY_CODE, countryCode.getValue());
        params.addValue(UsedCarStockTransfer.PARAM_DEALER_NO, dealerNumber.getValue());

        final String query = READ_USED_CAR_STOCK_TRANSFER
            + PRIMARY_KEY_CLAUSE;

        final List<UsedCarStockTransfer> factoryCarOrders = mwinNamedTemplate.query(query,
            params, new UsedCarStockTransferMapper());

        return DaoHelper.getSingleResult(factoryCarOrders);
    }
}
