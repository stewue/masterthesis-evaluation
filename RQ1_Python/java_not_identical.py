import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv',dtype='str')

prefilter = data[data['mainRepo'] == 'true']
print("number of projects " + str(prefilter.shape[0]))
values = prefilter[prefilter['javaTarget'].notnull()][prefilter['javaSource'].notnull()]

print("both set " + str(values.shape[0]))
filtered = values[values['javaTarget'] != values['javaSource']]

print("not same value " + str(filtered.shape[0]))