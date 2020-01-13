import numpy as np
from scipy import stats

def spearman(data, col1, col2):
    cleaned1 = data[np.logical_not(np.isnan(data[col1]))]
    cleaned2 = cleaned1[np.logical_not(np.isnan(cleaned1[col2]))]

    res = stats.spearmanr(cleaned2[col1], cleaned2[col2])
    corr = str("{0:.2f}".format(res.correlation))
    pvalue = res.pvalue
    extra = "\\phantom{**}"

    if pvalue < 0.01:
        extra = "**"
    elif pvalue < 0.05:
        extra = "*\\phantom{*}"

    return ["\correlationColor{" + corr + "}" + extra]

def pointbiserialr(data, col1, col2):
    res = stats.pointbiserialr(data[col1], data[col2])
    corr = str("{0:.2f}".format(res.correlation))
    pvalue = res.pvalue
    extra = "\\phantom{**}"

    if pvalue < 0.01:
        extra = "**"
    elif pvalue < 0.05:
        extra = "*\\phantom{*}"

    return ["\correlationColor{" + corr + "}" + extra]

def dfOutput(df):
    return df.to_csv(index=False).replace(",", " & ").replace("\r", " \\\\ \r")