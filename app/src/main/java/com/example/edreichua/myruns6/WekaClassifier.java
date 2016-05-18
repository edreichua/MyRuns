package com.example.edreichua.myruns6;


public class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N8990dca0(i);
        return p;
    }
    static double N8990dca0(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 291.379205) {
            p = WekaClassifier.N6430f6cd1(i);
        } else if (((Double) i[0]).doubleValue() > 291.379205) {
            p = WekaClassifier.N23867daa32(i);
        }
        return p;
    }
    static double N6430f6cd1(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 99.859686) {
            p = WekaClassifier.N570151b22(i);
        } else if (((Double) i[0]).doubleValue() > 99.859686) {
            p = WekaClassifier.N752dfe5f5(i);
        }
        return p;
    }
    static double N570151b22(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 79.453641) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 79.453641) {
            p = WekaClassifier.N567f549d3(i);
        }
        return p;
    }
    static double N567f549d3(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 0;
        } else if (((Double) i[10]).doubleValue() <= 1.222391) {
            p = WekaClassifier.N1648e4c54(i);
        } else if (((Double) i[10]).doubleValue() > 1.222391) {
            p = 0;
        }
        return p;
    }
    static double N1648e4c54(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() <= 7.998854) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() > 7.998854) {
            p = 1;
        }
        return p;
    }
    static double N752dfe5f5(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 25.725614) {
            p = WekaClassifier.N28fcad896(i);
        } else if (((Double) i[4]).doubleValue() > 25.725614) {
            p = WekaClassifier.N4841f43826(i);
        }
        return p;
    }
    static double N28fcad896(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 174.576406) {
            p = WekaClassifier.N40bdfb77(i);
        } else if (((Double) i[0]).doubleValue() > 174.576406) {
            p = WekaClassifier.N834ddcb14(i);
        }
        return p;
    }
    static double N40bdfb77(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 1;
        } else if (((Double) i[13]).doubleValue() <= 5.319733) {
            p = WekaClassifier.N55346d3f8(i);
        } else if (((Double) i[13]).doubleValue() > 5.319733) {
            p = 0;
        }
        return p;
    }
    static double N55346d3f8(Object []i) {
        double p = Double.NaN;
        if (i[18] == null) {
            p = 1;
        } else if (((Double) i[18]).doubleValue() <= 3.126954) {
            p = WekaClassifier.N4f71aec09(i);
        } else if (((Double) i[18]).doubleValue() > 3.126954) {
            p = WekaClassifier.N6c6e2cc613(i);
        }
        return p;
    }
    static double N4f71aec09(Object []i) {
        double p = Double.NaN;
        if (i[25] == null) {
            p = 1;
        } else if (((Double) i[25]).doubleValue() <= 0.331988) {
            p = 1;
        } else if (((Double) i[25]).doubleValue() > 0.331988) {
            p = WekaClassifier.N2acd24210(i);
        }
        return p;
    }
    static double N2acd24210(Object []i) {
        double p = Double.NaN;
        if (i[31] == null) {
            p = 1;
        } else if (((Double) i[31]).doubleValue() <= 0.861211) {
            p = WekaClassifier.N588043c711(i);
        } else if (((Double) i[31]).doubleValue() > 0.861211) {
            p = 1;
        }
        return p;
    }
    static double N588043c711(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 4.125627) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 4.125627) {
            p = WekaClassifier.N2eab907d12(i);
        }
        return p;
    }
    static double N2eab907d12(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 27.629151) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() > 27.629151) {
            p = 1;
        }
        return p;
    }
    static double N6c6e2cc613(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 6.087372) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() > 6.087372) {
            p = 1;
        }
        return p;
    }
    static double N834ddcb14(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 5.026026) {
            p = WekaClassifier.N5270397115(i);
        } else if (((Double) i[7]).doubleValue() > 5.026026) {
            p = WekaClassifier.N13cb3b0c18(i);
        }
        return p;
    }
    static double N5270397115(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 10.398167) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() > 10.398167) {
            p = WekaClassifier.N20aa733a16(i);
        }
        return p;
    }
    static double N20aa733a16(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 4.363139) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() > 4.363139) {
            p = WekaClassifier.N3dbf63f517(i);
        }
        return p;
    }
    static double N3dbf63f517(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() <= 3.654049) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() > 3.654049) {
            p = 0;
        }
        return p;
    }
    static double N13cb3b0c18(Object []i) {
        double p = Double.NaN;
        if (i[24] == null) {
            p = 2;
        } else if (((Double) i[24]).doubleValue() <= 1.008794) {
            p = WekaClassifier.N402323b519(i);
        } else if (((Double) i[24]).doubleValue() > 1.008794) {
            p = WekaClassifier.N7505d67a21(i);
        }
        return p;
    }
    static double N402323b519(Object []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 1;
        } else if (((Double) i[29]).doubleValue() <= 0.983667) {
            p = WekaClassifier.N3363ab2820(i);
        } else if (((Double) i[29]).doubleValue() > 0.983667) {
            p = 2;
        }
        return p;
    }
    static double N3363ab2820(Object []i) {
        double p = Double.NaN;
        if (i[26] == null) {
            p = 2;
        } else if (((Double) i[26]).doubleValue() <= 0.354875) {
            p = 2;
        } else if (((Double) i[26]).doubleValue() > 0.354875) {
            p = 1;
        }
        return p;
    }
    static double N7505d67a21(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 235.053695) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 235.053695) {
            p = WekaClassifier.N3fdc908d22(i);
        }
        return p;
    }
    static double N3fdc908d22(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 1;
        } else if (((Double) i[9]).doubleValue() <= 12.505064) {
            p = WekaClassifier.N663b324823(i);
        } else if (((Double) i[9]).doubleValue() > 12.505064) {
            p = 2;
        }
        return p;
    }
    static double N663b324823(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 2.054948) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() > 2.054948) {
            p = WekaClassifier.N48afef4c24(i);
        }
        return p;
    }
    static double N48afef4c24(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 1;
        } else if (((Double) i[17]).doubleValue() <= 3.667207) {
            p = 1;
        } else if (((Double) i[17]).doubleValue() > 3.667207) {
            p = WekaClassifier.N1342462525(i);
        }
        return p;
    }
    static double N1342462525(Object []i) {
        double p = Double.NaN;
        if (i[27] == null) {
            p = 2;
        } else if (((Double) i[27]).doubleValue() <= 2.656617) {
            p = 2;
        } else if (((Double) i[27]).doubleValue() > 2.656617) {
            p = 1;
        }
        return p;
    }
    static double N4841f43826(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 20.338141) {
            p = WekaClassifier.N7757360a27(i);
        } else if (((Double) i[8]).doubleValue() > 20.338141) {
            p = 0;
        }
        return p;
    }
    static double N7757360a27(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 49.287087) {
            p = WekaClassifier.N4035622328(i);
        } else if (((Double) i[2]).doubleValue() > 49.287087) {
            p = 1;
        }
        return p;
    }
    static double N4035622328(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 9.182304) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() > 9.182304) {
            p = WekaClassifier.N41c7676e29(i);
        }
        return p;
    }
    static double N41c7676e29(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 29.385212) {
            p = WekaClassifier.N6e0bb5a30(i);
        } else if (((Double) i[4]).doubleValue() > 29.385212) {
            p = 2;
        }
        return p;
    }
    static double N6e0bb5a30(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 15.545525) {
            p = WekaClassifier.N48c92aea31(i);
        } else if (((Double) i[6]).doubleValue() > 15.545525) {
            p = 2;
        }
        return p;
    }
    static double N48c92aea31(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 11.650974) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 11.650974) {
            p = 1;
        }
        return p;
    }
    static double N23867daa32(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 488.75317) {
            p = WekaClassifier.N590092a333(i);
        } else if (((Double) i[0]).doubleValue() > 488.75317) {
            p = WekaClassifier.N1d9a137258(i);
        }
        return p;
    }
    static double N590092a333(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 7.506804) {
            p = WekaClassifier.N5ab9c8b234(i);
        } else if (((Double) i[4]).doubleValue() > 7.506804) {
            p = WekaClassifier.N16956cd437(i);
        }
        return p;
    }
    static double N5ab9c8b234(Object []i) {
        double p = Double.NaN;
        if (i[18] == null) {
            p = 2;
        } else if (((Double) i[18]).doubleValue() <= 0.867709) {
            p = 2;
        } else if (((Double) i[18]).doubleValue() > 0.867709) {
            p = WekaClassifier.N5b26377535(i);
        }
        return p;
    }
    static double N5b26377535(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 25.38568) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 25.38568) {
            p = WekaClassifier.N4b5d6a9636(i);
        }
        return p;
    }
    static double N4b5d6a9636(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 17.766536) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() > 17.766536) {
            p = 2;
        }
        return p;
    }
    static double N16956cd437(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 112.486987) {
            p = WekaClassifier.N27ddaec38(i);
        } else if (((Double) i[1]).doubleValue() > 112.486987) {
            p = WekaClassifier.N490562f656(i);
        }
        return p;
    }
    static double N27ddaec38(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 12.541244) {
            p = WekaClassifier.N680ac84939(i);
        } else if (((Double) i[7]).doubleValue() > 12.541244) {
            p = WekaClassifier.N45f50df550(i);
        }
        return p;
    }
    static double N680ac84939(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 2;
        } else if (((Double) i[20]).doubleValue() <= 5.5783) {
            p = WekaClassifier.N4823160440(i);
        } else if (((Double) i[20]).doubleValue() > 5.5783) {
            p = 1;
        }
        return p;
    }
    static double N4823160440(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 388.238461) {
            p = WekaClassifier.N75ed201841(i);
        } else if (((Double) i[0]).doubleValue() > 388.238461) {
            p = 2;
        }
        return p;
    }
    static double N75ed201841(Object []i) {
        double p = Double.NaN;
        if (i[22] == null) {
            p = 2;
        } else if (((Double) i[22]).doubleValue() <= 3.059503) {
            p = WekaClassifier.N192b3ef342(i);
        } else if (((Double) i[22]).doubleValue() > 3.059503) {
            p = 1;
        }
        return p;
    }
    static double N192b3ef342(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 31.980812) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 31.980812) {
            p = WekaClassifier.N275df51f43(i);
        }
        return p;
    }
    static double N275df51f43(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 2.754841) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 2.754841) {
            p = WekaClassifier.N779e54c644(i);
        }
        return p;
    }
    static double N779e54c644(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 2;
        } else if (((Double) i[13]).doubleValue() <= 1.652287) {
            p = WekaClassifier.N6b6f57cd45(i);
        } else if (((Double) i[13]).doubleValue() > 1.652287) {
            p = WekaClassifier.N2bf320f546(i);
        }
        return p;
    }
    static double N6b6f57cd45(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 1;
        } else if (((Double) i[20]).doubleValue() <= 0.461118) {
            p = 1;
        } else if (((Double) i[20]).doubleValue() > 0.461118) {
            p = 2;
        }
        return p;
    }
    static double N2bf320f546(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 19.674117) {
            p = WekaClassifier.N63cedd9947(i);
        } else if (((Double) i[5]).doubleValue() > 19.674117) {
            p = WekaClassifier.N77b37f7e49(i);
        }
        return p;
    }
    static double N63cedd9947(Object []i) {
        double p = Double.NaN;
        if (i[18] == null) {
            p = 1;
        } else if (((Double) i[18]).doubleValue() <= 1.944137) {
            p = WekaClassifier.N66739d4248(i);
        } else if (((Double) i[18]).doubleValue() > 1.944137) {
            p = 1;
        }
        return p;
    }
    static double N66739d4248(Object []i) {
        double p = Double.NaN;
        if (i[15] == null) {
            p = 1;
        } else if (((Double) i[15]).doubleValue() <= 2.164242) {
            p = 1;
        } else if (((Double) i[15]).doubleValue() > 2.164242) {
            p = 2;
        }
        return p;
    }
    static double N77b37f7e49(Object []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 1;
        } else if (((Double) i[29]).doubleValue() <= 1.052466) {
            p = 1;
        } else if (((Double) i[29]).doubleValue() > 1.052466) {
            p = 2;
        }
        return p;
    }
    static double N45f50df550(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 2;
        } else if (((Double) i[17]).doubleValue() <= 5.535125) {
            p = 2;
        } else if (((Double) i[17]).doubleValue() > 5.535125) {
            p = WekaClassifier.N504222c751(i);
        }
        return p;
    }
    static double N504222c751(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 1;
        } else if (((Double) i[11]).doubleValue() <= 5.152653) {
            p = 1;
        } else if (((Double) i[11]).doubleValue() > 5.152653) {
            p = WekaClassifier.N2a213c0052(i);
        }
        return p;
    }
    static double N2a213c0052(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 9.893745) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() > 9.893745) {
            p = WekaClassifier.N4685eeb553(i);
        }
        return p;
    }
    static double N4685eeb553(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 1;
        } else if (((Double) i[20]).doubleValue() <= 4.882548) {
            p = 1;
        } else if (((Double) i[20]).doubleValue() > 4.882548) {
            p = WekaClassifier.N2b72d93554(i);
        }
        return p;
    }
    static double N2b72d93554(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 82.635852) {
            p = WekaClassifier.N6a67928655(i);
        } else if (((Double) i[1]).doubleValue() > 82.635852) {
            p = 1;
        }
        return p;
    }
    static double N6a67928655(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 18.65491) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 18.65491) {
            p = 2;
        }
        return p;
    }
    static double N490562f656(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 17.836285) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() > 17.836285) {
            p = WekaClassifier.N209ccd2557(i);
        }
        return p;
    }
    static double N209ccd2557(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 12.255192) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() > 12.255192) {
            p = 1;
        }
        return p;
    }
    static double N1d9a137258(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() <= 11.244817) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() > 11.244817) {
            p = WekaClassifier.N4b3f41e559(i);
        }
        return p;
    }
    static double N4b3f41e559(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 65.891798) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 65.891798) {
            p = WekaClassifier.N29cf88a660(i);
        }
        return p;
    }
    static double N29cf88a660(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 591.325346) {
            p = WekaClassifier.N6f7c8d5f61(i);
        } else if (((Double) i[0]).doubleValue() > 591.325346) {
            p = 2;
        }
        return p;
    }
    static double N6f7c8d5f61(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 2;
        } else if (((Double) i[13]).doubleValue() <= 6.561753) {
            p = WekaClassifier.N22b0124162(i);
        } else if (((Double) i[13]).doubleValue() > 6.561753) {
            p = WekaClassifier.N13314f9b63(i);
        }
        return p;
    }
    static double N22b0124162(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 6.940511) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 6.940511) {
            p = 2;
        }
        return p;
    }
    static double N13314f9b63(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 18.582215) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() > 18.582215) {
            p = WekaClassifier.N466e6fe364(i);
        }
        return p;
    }
    static double N466e6fe364(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 1;
        } else if (((Double) i[13]).doubleValue() <= 9.920057) {
            p = 1;
        } else if (((Double) i[13]).doubleValue() > 9.920057) {
            p = WekaClassifier.N4d73040465(i);
        }
        return p;
    }
    static double N4d73040465(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 42.878611) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() > 42.878611) {
            p = 1;
        }
        return p;
    }
}

