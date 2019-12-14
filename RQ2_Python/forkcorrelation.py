import pandas as pd
import numpy as np
from scipy import stats

data = pd.read_csv('D:\\forkcorrelation.csv', delimiter=";")

corr = stats.spearmanr(data['changeRateCov'], data['forksCov'])
print("COV")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))
print("")

corr = stats.spearmanr(data['changeRateCi'], data['forksCi'])
print("CI")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))
print("")

corr = stats.spearmanr(data['changeRateDivergence'], data['forksDivergence'])
print("Divergence")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))