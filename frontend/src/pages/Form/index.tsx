import {useParams} from "react-router-dom";
import {Movie} from "../../types/Movie";
import FormCard from "../../components/FormCard";

function Form() {

    const params = useParams()


    return (
        <FormCard movieId={`${params.movieId}`} />
    );
}

export default Form;