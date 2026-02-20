package tn.esprit.spring.crudetudiant.config;

import org.hibernate.dialect.H2Dialect;

/**
 * Classe pour gérer la compatibilité entre MySQL et H2 dans les tests.
 * H2 en mode MySQL ne supporte pas la directive engine=MyISAM
 */
public class H2CompatibleDialect extends H2Dialect {

    @Override
    public String getTableTypeString() {
        // H2 ne supporte pas la directive engine, donc on la désactive
        return "";
    }
}

